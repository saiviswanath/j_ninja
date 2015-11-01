package com.ldap;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.DefaultPoolableLdapConnectionFactory;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.template.EntryMapper;
import org.apache.directory.ldap.client.template.LdapConnectionTemplate;

public class LDAPConnectionTemplateManager {

  private LDAPConnectionTemplateManager() {}

  public static LdapConnectionTemplate getLDAPConnectionTemplate() {
    LdapConnectionConfig config = new LdapConnectionConfig();
    config.setLdapHost(config.getDefaultLdapHost());
    config.setLdapPort(config.getDefaultLdapPort());
    config.setName("cn=Manager,dc=maxcrc,dc=com");
    config.setCredentials("secret"); // Encrypt don't work?

    DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(config);
    // factory.setTimeOut(2000); // Overridden timeout. Let it be 30 sec

    GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
    poolConfig.lifo = true;
    poolConfig.maxActive = 8; // Reconfigure.
    poolConfig.maxIdle = 8;
    poolConfig.maxWait = -1L;
    poolConfig.minEvictableIdleTimeMillis = 1000L * 60L * 30L;
    poolConfig.minIdle = 0;
    poolConfig.numTestsPerEvictionRun = 3;
    poolConfig.softMinEvictableIdleTimeMillis = -1L; // Indefinate block if pool exhausts.
    poolConfig.testOnBorrow = false;
    poolConfig.testOnReturn = false;
    poolConfig.testWhileIdle = false;
    poolConfig.timeBetweenEvictionRunsMillis = -1L;
    poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

    return new LdapConnectionTemplate(new LdapConnectionPool(
        new DefaultPoolableLdapConnectionFactory(factory), poolConfig));
  }

  public static void main(String[] args) {

    LdapConnectionTemplate template = LDAPConnectionTemplateManager.getLDAPConnectionTemplate();
    String email = template.lookup( template.newDn( "uid=jjones,ou=people,dc=maxcrc,dc=com" ),
        null, new EntryMapper<String>() {

      @Override public String map( Entry entry ) throws LdapException { return entry.get( "sn"
          ).getString(); } } );

    System.out.println(email.split(","));

  }

}
