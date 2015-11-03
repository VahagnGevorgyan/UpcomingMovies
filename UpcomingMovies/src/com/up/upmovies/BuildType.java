package com.up.upmovies;

public enum BuildType {
	DEBUGGING("dev",
            "dev",
            Config.UP_HOST_DEBUG,
            true),

    RELEASE("111",
  		"111",
          Config.UP_HOST_RELEASE,
          false);
	
	private final String login;
    private final String password;
    private final String host;
    private final boolean debugging;

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * Returns host of the server to be used for this kind of build
     * for obtain promo pages and redirect links
     * @return - Host to access promo pages and redirect links
     */
    public String getHost() {
        return this.host;
    }

    /**
     * @return true if DEBUGGING is enabled for this build type
     */
    public boolean isDebugging() {
        return this.debugging;
    }
    
    /**
     * Constructor
     * @param label - Resource ID of build
     *     label to display in about screen
     * @param rmcHost - host of server to be used to access to MiTV api and assets
     * @param host - host to be used to access promo pages and redirect links
     */
    private BuildType(String login,
                      String password,
                      String host,
                      boolean debugging) {
        this.login = login;
        this.password = password;
        this.host = host;
        this.debugging = debugging;
    }
    
}
