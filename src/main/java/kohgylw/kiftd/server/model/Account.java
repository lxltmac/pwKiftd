package kohgylw.kiftd.server.model;

import lombok.Data;

@Data
public class Account {

    private String className;

    private String name;

    private String accountNumber;

    private String pwd;

    private String auth;

    private String group;

    private String folder;
}
