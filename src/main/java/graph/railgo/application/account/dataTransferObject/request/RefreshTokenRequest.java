package graph.railgo.application.account.dataTransferObject.request;

public class RefreshTokenRequest {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
