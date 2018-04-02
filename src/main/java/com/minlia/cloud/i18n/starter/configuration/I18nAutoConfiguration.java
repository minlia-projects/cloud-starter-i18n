package com.minlia.cloud.i18n.starter.configuration;


import com.minlia.cloud.i18n.properties.I18nProperties;
import com.minlia.cloud.i18n.source.JdbcMessageSource;
import com.minlia.cloud.i18n.resolver.AcceptHeaderLocaleResolver;
import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Created by will on 6/19/17. 启动时延迟此BEAN初始化
 */
@Configuration
@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(prefix = "system.i18n", name = {"enabled"}, havingValue = "true")
public class I18nAutoConfiguration {


  @Configuration
  public static class LocaleConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      registry.addInterceptor(localeChangeInterceptor);
    }
  }

  @Configuration
  public static class SystemLocaleConfiguration implements EnvironmentAware {

    private static final String DEFAULT_SELECT_ONE_I18N_ITEM_SQL = "select message from system_i18n where code = ? and language = ? and country = ? limit 1";
    @Autowired
    private DataSource dataSource;
    @Autowired
    private I18nProperties i18nProperties;

    @Bean
    @ConditionalOnMissingBean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate()
        throws Exception {
      return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSource messageSource() throws Exception {
      JdbcMessageSource messageSource = new JdbcMessageSource();
      messageSource
          .setNamedParameterJdbcOperations(namedParameterJdbcTemplate());
      //使用code代替已翻译的结果
      messageSource.setUseCodeAsDefaultMessage(true);

      //可选方案： 从单条语句的定制化到 i18nItemService的自定义
      if (StringUtils.isEmpty(i18nProperties.getSelectOneI18nItemSql())) {
        messageSource.setSqlStatement(DEFAULT_SELECT_ONE_I18N_ITEM_SQL);
      } else {
        messageSource.setSqlStatement(i18nProperties.getSelectOneI18nItemSql());
      }
      messageSource
          .setCachedMilliSecond(i18nProperties.getCachedMilliSeconds());//long最长支持24天的毫秒数, 内部实现的缓存
      messageSource.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
      return messageSource;
    }

    @Lazy
    @Bean(name = "localeResolver")
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
      AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
      localeResolver.setDefaultLocale(Locale.getDefault());
      return localeResolver;
    }

    @Bean
    @ConditionalOnMissingBean
    public LocalValidatorFactoryBean validator() throws Exception {
      LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
      bean.setValidationMessageSource(messageSource());
      return bean;
    }


    @Override
    public void setEnvironment(Environment environment) {
    }
  }

}
