package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.domain.rs.ShiroExt;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
@Repository("oauthJdbcRepository")
public class OauthJdbcRepository extends AbstractJdbcRepository implements OauthRepository {


    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();
    private static OauthCodeRowMapper oauthCodeRowMapper = new OauthCodeRowMapper();

    private AccessTokenRowMapper accessTokenRowMapper = new AccessTokenRowMapper();


    @Override
    public ClientDetails findClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveClientDetails(final ClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,client_secret,client_name, client_uri,client_icon_uri,resource_ids, scope,grant_types, " +
                "redirect_uri,roles,access_token_validity,refresh_token_validity,description,archived,trusted) values (?,?,?, ?,?,?,?,?, ?,?, ?,? ,?,?,?)";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clientDetails.getClientId());
                ps.setString(2, clientDetails.getClientSecret());
                ps.setString(3, clientDetails.getName());

                ps.setString(4, clientDetails.getClientUri());
                ps.setString(5, clientDetails.getIconUri());
                ps.setString(6, clientDetails.resourceIds());

                ps.setString(7, clientDetails.scope());
                ps.setString(8, clientDetails.grantTypes());
                ps.setString(9, clientDetails.getRedirectUri());

                ps.setString(10, clientDetails.roles());
                ps.setInt(11, clientDetails.accessTokenValidity() == null ? -1 : clientDetails.accessTokenValidity());
                ps.setInt(12, clientDetails.refreshTokenValidity() == null ? -1 : clientDetails.refreshTokenValidity());

                ps.setString(13, clientDetails.getDescription());
                ps.setBoolean(14, clientDetails.archived());
                ps.setBoolean(15, clientDetails.trusted());
            }
        });
    }

    @Override
    public int saveOauthCode(final OauthCode oauthCode) {
        final String sql = " insert into oauth_code(code,username,client_id) values (?,?,?)";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.username());
                ps.setString(3, oauthCode.clientId());
            }
        });
    }

    @Override
    public OauthCode findOauthCode(String code, String clientId) {
        final String sql = " select * from oauth_code where code = ? and client_id = ? ";
        final List<OauthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, code, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public OauthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        final String sql = " select * from oauth_code where username = ? and client_id = ? ";
        final List<OauthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, username, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteOauthCode(final OauthCode oauthCode) {
        final String sql = " delete from oauth_code where code = ? and client_id = ? and username = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.clientId());
                ps.setString(3, oauthCode.username());
            }
        });
    }

    @Override
    public AccessToken findAccessToken(String clientId, String username, String authenticationId) {
        final String sql = " select * from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, clientId, username, authenticationId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteAccessToken(final AccessToken accessToken) {
        final String sql = " delete from oauth_access_token where client_id = ? and username = ? and authentication_id = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.clientId());
                ps.setString(2, accessToken.username());
                ps.setString(3, accessToken.authenticationId());
            }
        });
    }

    @Override
    public int saveAccessToken(final AccessToken accessToken) {
        final String sql = "insert into oauth_access_token(token_id,token_expired_seconds,authentication_id," +
                "username,client_id,token_type,refresh_token_expired_seconds,refresh_token) values (?,?,?,?,?,?,?,?) ";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, accessToken.tokenId());
                ps.setInt(2, accessToken.tokenExpiredSeconds());
                ps.setString(3, accessToken.authenticationId());

                ps.setString(4, accessToken.username());
                ps.setString(5, accessToken.clientId());
                ps.setString(6, accessToken.tokenType());

                ps.setInt(7, accessToken.refreshTokenExpiredSeconds());
                ps.setString(8, accessToken.refreshToken());
            }
        });
    }

    @Override
    public AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId) {
        final String sql = " select * from oauth_access_token where refresh_token = ? and client_id = ? ";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, refreshToken, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean createUser(final String username, final String pwd) {


        final String sql = "insert into users(username,password,create_time,guid) values(?,?,now(),?) ";
        final String pw = new SimpleHash("md5", pwd, null, 1).toHex();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, username);
                                    ps.setString(2, pw);
                                    ps.setString(3, UUID.randomUUID().toString());

                                    return ps;
                                }
                            },
                keyHolder);
        final Long id = keyHolder.getKey().longValue();
        if(id==null)return  false;

        String insertSql ="insert into user_roles(users_id,roles_id) values(?,22)";

        return jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        })>0;
    }

    @Override
    public boolean pwd(final String username, String pwd) {
        final String pw = new SimpleHash("md5", pwd, null, 1).toHex();

        final String sql = " update  users set password=?  where  username = ?  ";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, pw);
                ps.setString(2, username);
            }
        })>0;
    }


}
