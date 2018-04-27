package in.co.tripin.chahiyecustomer.Model.responce;


import in.co.tripin.chahiyecustomer.factory.Response;

public class OAuthResponse implements Response {
    private String status;
    private String message;
    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     *
     * @param access_token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     *
     * @return
     */
    public String getExpires_in() {
        return expires_in;
    }

    /**
     *
     * @param expires_in
     */
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    /**
     *
     * @return
     */
    public String getToken_type() {
        return token_type;
    }

    /**
     *
     * @param token_type
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    /**
     *
     * @return
     */
    public String getScope() {
        return scope;
    }

    /**
     *
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
