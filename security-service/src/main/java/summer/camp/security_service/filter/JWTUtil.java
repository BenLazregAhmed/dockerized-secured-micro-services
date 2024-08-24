package summer.camp.security_service.filter;

import java.util.Date;

public class JWTUtil {
    public static final String SECRET="MySecretKey";
    public static final String AUTH_HEADER="Authorization";
    public static final Long EXPIRES_ACCESS_TOKEN= new Date(System.currentTimeMillis()+10*60*1000).getTime();
    public static final Long EXPIRES_REFRESH_TOKEN= new Date(System.currentTimeMillis()+30*60*1000).getTime();
    public static final String PREFIX= "Bearer ";


}
