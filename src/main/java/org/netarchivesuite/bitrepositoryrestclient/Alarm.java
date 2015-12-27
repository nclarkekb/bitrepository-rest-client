package org.netarchivesuite.bitrepositoryrestclient;

import com.antiaction.common.json.annotation.JSONNullable;

public class Alarm {

    public static final String ALL = "ALL";
    public static final String INCONSISTENT_REQUEST = "INCONSISTENT_REQUEST";
    public static final String COMPONENT_FAILURE = "COMPONENT_FAILURE";
    public static final String CHECKSUM_ALARM = "CHECKSUM_ALARM";
    public static final String FAILED_TRANSFER = "FAILED_TRANSFER";
    public static final String FAILED_OPERATION = "FAILED_OPERATION";
    public static final String INVALID_MESSAGE = "INVALID_MESSAGE";
    public static final String INVALID_MESSAGE_VERSION = "INVALID_MESSAGE_VERSION";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String INTEGRITY_ISSUE = "INTEGRITY_ISSUE";

    public long OrigDateTime;

    public String AlarmCode;

    public String AlarmRaiser;

    public String AlarmText;

    @JSONNullable
    public String CollectionID;

    @JSONNullable
    public String FileID;

}
