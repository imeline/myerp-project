package erp.auth.mapper;

import erp.auth.domain.RefreshToken;
import erp.auth.dto.internal.RefreshTokenRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {

    long nextId();

    int save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(@Param("token") String token);

    Optional<RefreshTokenRow> findRefreshTokenRowByUuid(
            @Param("uuid") String uuid);

    int updateTokenByErpAccountId(@Param("erpAccountId") long erpAccountId,
                                  @Param("token") String token);

    int deleteById(@Param("refreshTokenId") long refreshTokenId);

    boolean existsByErpAccountId(@Param("erpAccountId") long erpAccountId);
}