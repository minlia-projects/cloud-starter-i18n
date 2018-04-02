package com.minlia.cloud.i18n.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "system.i18n")
public class I18nProperties {

  private Boolean enabled = Boolean.TRUE;

  private Long cachedMilliSeconds = 2073600000L;

//    @NotNull
//    private String tableName="system_i18n";

  private String selectOneI18nItemSql = "select message from system_i18n where code = ? and language = ? and country = ? limit 1";


}
