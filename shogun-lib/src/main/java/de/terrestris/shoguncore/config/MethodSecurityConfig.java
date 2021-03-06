package de.terrestris.shoguncore.config;

import de.terrestris.shoguncore.security.access.BasePermissionEvaluator;
import de.terrestris.shoguncore.security.access.entity.BaseEntityPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO Move to shogun-boot?!
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan(basePackages = { "de.terrestris.shogunboot", "de.terrestris.shoguncore" })
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private BasePermissionEvaluator basePermissionEvaluator;

    @Autowired
    private List<BaseEntityPermissionEvaluator> permissionEvaluators;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        //expressionHandler.setRoleHierarchy();
//        BasePermissionEvaluator basePermissionEvaluator = new BasePermissionEvaluator();
//        basePermissionEvaluator.setPermissionEvaluatorFactory(permissionEvaluatorFactory);
        expressionHandler.setPermissionEvaluator(basePermissionEvaluator);

        return expressionHandler;
    }

//    @Bean
//    public PermissionEvaluator permissionEvaluator() {
//        Map<String, PermissionEvaluator> map = new HashMap<>();
//
//        // Build lookup table of PermissionEvaluator by supported target type
//        for (BaseEntityPermissionEvaluator permissionEvaluator : permissionEvaluators) {
//            map.put(permissionEvaluator.getTargetType(), permissionEvaluator);
//        }
//
//        return new BasePermissionEvaluator(map);
//    }
}
