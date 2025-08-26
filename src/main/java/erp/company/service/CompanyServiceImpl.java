package erp.company.service;

import erp.company.domain.Company;
import erp.company.dto.internal.CompanyRow;
import erp.company.dto.request.AddCompanyRequest;
import erp.company.dto.request.GetCompanyListRequest;
import erp.company.dto.request.ModifyCompanyRequest;
import erp.company.dto.response.CompanyInfoResponse;
import erp.company.dto.response.GetCompanyListResponse;
import erp.company.mapper.CompanyMapper;
import erp.global.exception.ErrorStatus;
import erp.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;

    @Transactional
    public void addCompany(AddCompanyRequest request) {
        validateBizNo(request.bizNo(), null);
        validateName(request.name(), null);

        long newId = companyMapper.nextId();
        Company company = Company.of(
                newId,
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affected = companyMapper.create(company);
        assertAffected(affected, ErrorStatus.CREATE_COMPANY_FAIL);
    }

    @Transactional(readOnly = true)
    public CompanyInfoResponse getCompany(long companyId) {
        Company company = companyMapper.findById(companyId);
        if (company == null)
            throw new GlobalException(ErrorStatus.NOT_FOUND_COMPANY);
        return CompanyInfoResponse.from(company);
    }

    @Transactional(readOnly = true)
    public GetCompanyListResponse<CompanyRow> listCompany(GetCompanyListRequest request) {
        int size = request.size();
        int page = request.page();
        String name = request.name();
        int offset = page * size;

        List<CompanyRow> rows = companyMapper.findCompanyRows(name, offset, request.size());
        long total = companyMapper.countByName(name);
        int totalPages = (int) Math.ceil(total / (double) size);
        boolean hasNext = (page + 1) < totalPages;

        return GetCompanyListResponse.of(rows, page, total, totalPages, hasNext);
    }

    @Transactional
    public void modifyCompany(ModifyCompanyRequest request) {
        // 중복 여부 체크
        validateBizNo(request.bizNo(), request.companyId());
        validateName(request.name(), request.companyId());

        Company company = Company.of(
                request.companyId(),
                request.name(),
                request.bizNo(),
                request.address(),
                request.phone()
        );

        int affected = companyMapper.update(company);
        assertAffected(affected, ErrorStatus.UPDATE_COMPANY_FAIL);
    }

    @Transactional
    public void deleteCompany(long companyId) {
        long related = companyMapper.countEmployees(companyId)
                + companyMapper.countOrders(companyId)
                + companyMapper.countOutbounds(companyId);
        if (related > 0)
            throw new GlobalException(ErrorStatus.EXTERNAL_DATA_EXISTS);

        int affected = companyMapper.deleteById(companyId);
        assertAffected(affected, ErrorStatus.DELETE_COMPANY_FAIL);
    }

    // 중복 검사
    private void validateBizNo(String bizNo, Long excludeId) {
        if (companyMapper.existsByBizNo(bizNo, excludeId))
            throw new GlobalException(ErrorStatus.DUPLICATE_BIZ_NO);
    }

    private void validateName(String name, Long excludeId) {
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
