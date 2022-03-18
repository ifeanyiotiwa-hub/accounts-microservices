package org.betpawa.accounts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
public class Properties {
    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;
    private final List<String> activeBranches;
    private Properties(AccountsPropertiesBuilder accountsPropertiesBuilder) {
        this.msg = accountsPropertiesBuilder.getMsg();
        this.buildVersion = accountsPropertiesBuilder.getBuildVersion();
        this.mailDetails = accountsPropertiesBuilder.getMailDetails();
        this.activeBranches = accountsPropertiesBuilder.getActiveBranches();
    }
    @Getter
    @Setter
    public static class AccountsPropertiesBuilder {

        private String msg;
        private String buildVersion;
        private Map<String, String> mailDetails;
        private List<String> activeBranches;

        public AccountsPropertiesBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }
        public AccountsPropertiesBuilder buildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
            return this;
        }

        public AccountsPropertiesBuilder mailDetails(Map<String,String> mailDetails) {
            this.mailDetails = mailDetails;
            return this;
        }

        public AccountsPropertiesBuilder activeBranches(List<String> activeBranches) {
            this.activeBranches = activeBranches;
            return this;
        }


        public Properties build() {
            return new Properties(this);
        }
    }
}
