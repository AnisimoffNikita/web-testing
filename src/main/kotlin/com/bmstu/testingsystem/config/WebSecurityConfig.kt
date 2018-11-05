package com.bmstu.testingsystem.config


import com.bmstu.testingsystem.security.SimpleUserDetailService
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.NoOpPasswordEncoder


@Configuration
@EnableWebSecurity
@ComponentScan("com.bmstu.testingsystem")
class WebSecurityConfig: WebSecurityConfigurerAdapter() {


    @Autowired
    private lateinit var userDetailsService: SimpleUserDetailService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .userDetailsService(userDetailsService)
                .and()
                .authenticationProvider(authenticationProvider())

    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/", "/sign_up", "/bootstrap/**", "/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/sign_in").defaultSuccessUrl("/mainpage").permitAll()
                .and()
                .logout().permitAll()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance())
        return authProvider
    }

}
