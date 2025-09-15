// CompanyServiceImpl.java
package erp.company.service;

import erp.company.domain.Company;
import erp.company.dto.internal.CompanyFindRow;
import erp.company.dto.request.CompanyFindAllRequest;
import erp.company.dto.request.CompanySaveRequest;
import erp.company.dto.request.CompanyUpdateRequest;
import erp.company.dto.response.CompanyFindResponse;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.mapper.CompanyMapper;
import erp.company.validation.CompanyValidator;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import erp.global.response.PageResponse;
import erp.global.util.PageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static erp.global.util.RowCountGuards.requireOneRowAffected;
import static erp.global.util.Strings.normalizeOrNull;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyValidator companyValidator;

    @Override
    @Transactional
    public Long saveCompany(CompanySaveRequest request) {
        companyValidator.validBizNoUnique(request.bizNo(), null);
        companyValidator.validNameUnique(request.name(), null);

        long newId = companyMapper.nextId();
        Company company = Company.of(
                newId,
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affectedRowCount = companyMapper.save(company);
        requireOneRowAffected(affectedRowCount, ErrorStatus.CREATE_COMPANY_FAIL);
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
    public PageResponse<CompanyFindResponse> findAllCompany(CompanyFindAllRequest request) {
        PageParam pageParam = PageParam.of(request.page(), request.size(), 20);
        String name = normalizeOrNull(request.name());

        List<CompanyFindRow> rows = companyMapper.findAllCompanyRow(name, pageParam.offset(), pageParam.size());
        if (rows.isEmpty())
            throw new GlobalException(ErrorStatus.NOT_REGISTERED_COMPANY);

        List<CompanyFindResponse> responses = rows.stream()
                .map(CompanyFindResponse::from)
                .toList();

        long total = companyMapper.countByName(name);
        return PageResponse.of(responses, pageParam.page(), total, pageParam.size());
    }

    @Override
    @Transactional
    public void updateCompany(Long companyId, CompanyUpdateRequest request) {
        // 중복 여부 체크
        companyValidator.validBizNoUnique(request.bizNo(), companyId);
        companyValidator.validNameUnique(request.name(), companyId);

        Company company = Company.of(
                companyId,
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affectedRowCount = companyMapper.updateById(company);
        requireOneRowAffected(affectedRowCount, ErrorStatus.UPDATE_COMPANY_FAIL);
    }

    @Override
    @Transactional
    public void softDeleteCompany(long companyId) {
        // todo: 직원, 품목, 주문, 발주 , 재고 연관 데이터(삭제된건 제외) 존재 여부 체크 추가 필요
//        long related = companyMapper.countEmployees(companyId) // 직원 다 삭제해야 삭제 가능
//                + companyMapper.countOrders(companyId)
//                + companyMapper.countOutbounds(companyId);
//        if (related > 0)
//            throw new GlobalException(ErrorStatus.EXTERNAL_DATA_EXISTS);

        int affectedRowCount = companyMapper.softDeleteById(companyId);
        requireOneRowAffected(affectedRowCount, ErrorStatus.DELETE_COMPANY_FAIL);
    }
}
