package com.share.greencloud.model;

/**
 * {
 * "id": "858725350950635",
 * "name": "Sarang Yang"
 * }
 * <p>
 * {
 * "error": {
 * "message": "Error validating access token: Session has expired on Tuesday, 30-Apr-19 09:00:00 PDT. The current time is Tuesday, 30-Apr-19 23:55:39 PDT.",
 * "type": "OAuthException",
 * "code": 190,
 * "error_subcode": 463,
 * "fbtrace_id": "GWEmiZ5RPxE"
 * }
 * }
 */
public class FBLoginModel {
    private String id;
    private String name;
    private Error error;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Error getError() { return error; }

    public void setError(Error error) { this.error = error; }

    public class Error {
        String message;
        String type;
        String code;
        String error_subcode;
        String fbtrace_id;

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }

        public String getType() { return type; }

        public void setType(String type) { this.type = type; }

        public String getCode() { return code; }

        public void setCode(String code) { this.code = code; }

        public String getError_subcode() { return error_subcode; }

        public void setError_subcode(String error_subcode) { this.error_subcode = error_subcode; }

        public String getFbtrace_id() { return fbtrace_id; }

        public void setFbtrace_id(String fbtrace_id) { this.fbtrace_id = fbtrace_id; }
    }
}
