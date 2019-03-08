package com.jack.springboot.email.config;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * @author duliu
 *
 */
@Configuration
//@EnableWebMvc
public class SpringMailConfig implements ApplicationContextAware, EnvironmentAware
{

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Bean
    public TemplateEngine htmlEmailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        //templateEngine.addTemplateResolver(textTemplateResolver());
        // Resolver for HTML emails (except the editable one)
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
        //templateEngine.addTemplateResolver(stringTemplateResolver());
        // Message source, internationalization specific to emails
        //templateEngine.setTemplateEngineMessageSource(emailMessageSource());

        return templateEngine;
    }

    @Bean
    public TemplateEngine textEmailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        templateEngine.addTemplateResolver(textTemplateResolver());
        // Resolver for HTML emails (except the editable one)
        //templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
        // templateEngine.addTemplateResolver(stringTemplateResolver());
        // Message source, internationalization specific to emails
        //templateEngine.setTemplateEngineMessageSource(emailMessageSource());

        return templateEngine;
    }

    /**
     * TEXT 模板解析器
     * @return
     */
    private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver  templateResolver = new ClassLoaderTemplateResolver();
        //templateResolver.setOrder(Integer.valueOf(1)); //优先级
        //templateResolver.setResolvablePatterns(Collections.singleton("text/*")); //文件类型
        templateResolver.setPrefix("/templates/"); //模板文件开始
        templateResolver.setSuffix(".txt"); //模板文件结尾
        templateResolver.setTemplateMode(TemplateMode.TEXT); //模板模型
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name()); //编码
        templateResolver.setCacheable(false); //缓存
        return templateResolver;
    }

    /**
     * html 模板解析器
     * @return
     */
    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        //templateResolver.setOrder(Integer.valueOf(2));
        //templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * html 字符串 类型  模板解析器
     * @return
     */
    private ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        //templateResolver.setOrder(Integer.valueOf(3));
        // No resolvable pattern, will simply process as a String template everything not previously matched
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Override
    public void setEnvironment(Environment environment) {
        // TODO Auto-generated method stub
        environment = this.environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        applicationContext = this.applicationContext;
    }

}
