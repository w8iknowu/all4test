package com.example.jwttest1;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * JWT测试，之后可删
 */
public class Test {

    private long time= 1000*60*60*24;
    private String signature="gKMDKcTdWpT6dhGrQDk305XlyVpf16c2";
    @org.junit.Test
    public void jwt()
    {
        JwtBuilder jwtBuilder = Jwts.builder();//静态对象进行初始化
        String jwtToken= jwtBuilder
                //设置header部分，定义类型typ与算法alg
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
                //在paylod设置所需的信息
                .claim("username", "tom")
                .claim("role", "0")
                .claim("login_user_key", "A1000-8686")
                //设置payload中的预设信息
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))//24小时过期
                .setId(UUID.randomUUID().toString())
                //设置signature
                .signWith(SignatureAlgorithm.HS512, "gKMDKcTdWpT6dhGrQDk305XlyVpf16c2")
                //拼接
                .compact();

        System.out.println(jwtToken);


    }



    @org.junit.Test
    public void parse()
    {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2ODE0NzE1NDIsImp0aSI6ImFjMjMyNDk0LWEwNmEtNGRiNy05MzVlLWQyZDFmYjRhZTRhNSJ9.Yb_NUCY201U08LYrb5F0Dy3swvxLc0N_betxXEspH4k";
        JwtParser jwtParser= Jwts.parser();
        Jws<Claims> cliamsJws= jwtParser.setSigningKey(signature).parseClaimsJws(token);//通过签名
        Claims claims= cliamsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());//签名
        System.out.println(claims.getExpiration());//日期
    }
}
