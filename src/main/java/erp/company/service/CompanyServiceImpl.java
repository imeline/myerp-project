package erp.company.service;

import erp.account.mapper.ErpAccountMapper;
import erp.company.domain.Company;
import erp.company.dto.internal.CompanyFindRow;
import erp.company.dto.request.CompanyFindAllRequest;
import erp.company.dto.request.CompanySaveRequest;
import erp.company.dto.request.CompanyUpdateRequest;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.mapper.CompanyMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;
    private final ErpAccountMapper erpAccountMapper;

    @Override
    @Transactional
    public Long saveCompany(CompanySaveRequest request) {
        validateBizNoUnique(request.bizNo(), null);
        validateNameUnique(request.name(), null);

        long newId = companyMapper.nextId();
        Company company = Company.of(
                newId,
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affectedRowCount = companyMapper.save(company);
        assertAffected(affectedRowCount, ErrorStatus.CREATE_COMPANY_FAIL);
        return newId;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyInfoResponse findCompany(long companyId) {
        Company company = companyMapper.findById(companyId)
                .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND_COMPANY));
        return CompanyInfoResponse.from(company);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CompanyFindRow> findAllCompany(CompanyFindAllRequest request) {
        int size = (request.size() == null || request.size() < 1) ? 20 : request.size();
        int page = (request.page() == null || request.page() < 0) ? 0 : request.page();
        int offset = page * size;

        String name = request.name();
        name = (name == null || name.isBlank()) ? null : name.trim();

        List<CompanyFindRow> rows = companyMapper.findAllCompanyRow(name, offset, size);
        if (rows.isEmpty()) {
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_COMPANY);
        }

        long total = companyMapper.countByName(name);
        return PageResponse.of(rows, page, total, size);
    }

    @Override
    @Transactional
    public void updateCompany(Long companyId, CompanyUpdateRequest request) {
        // 중복 여부 체크
        validateBizNoUnique(request.bizNo(), companyId);
        validateNameUnique(request.name(), companyId);

        Company company = Company.of(
                companyId,
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affectedRowCount = companyMapper.updateById(company);
        assertAffected(affectedRowCount, ErrorStatus.UPDATE_COMPANY_FAIL);
    }

    @Override
    @Transactional
    public void softDeleteCompany(long companyId) {
        // todo: 연관 데이터(삭제된건 제외) 존재 여부 체크 추가 필요
//        long related = companyMapper.countEmployees(companyId)
//                + companyMapper.countOrders(companyId)
//                + companyMapper.countOutbounds(companyId);
//        if (related > 0)
//            throw new GlobalException(ErrorStatus.EXTERNAL_DATA_EXISTS);

        int affectedRowCount = companyMapper.softDeleteById(companyId);
        assertAffected(affectedRowCount, ErrorStatus.DELETE_COMPANY_FAIL);
        // 해당 회사 직원들의 erp_account 도 소프트 삭제
        erpAccountMapper.softDeleteByCompanyId(companyId);
    }

    // 중복 검사
    private void validateBizNoUnique(String bizNo, Long excludeId) {
        if (companyMapper.existsByBizNo(bizNo, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_BIZ_NO);
    }

    private void validateNameUnique(String name, Long excludeId) {
        if (companyMapper.existsByName(name, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_NAME);
    }

    // create, update, delete SQL 실패 검사
    private void assertAffected(int affected, ErrorStatus status) {
        if (affected != 1) {
            throw new GlobalException(status);
        }
    }
}
