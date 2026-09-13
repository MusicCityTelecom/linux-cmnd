/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.squareup.moshi.Json
 *  okhttp3.Interceptor
 *  okhttp3.OkHttpClient
 *  okhttp3.OkHttpClient$Builder
 *  okhttp3.Request
 *  okhttp3.ResponseBody
 *  okhttp3.logging.HttpLoggingInterceptor
 *  okhttp3.logging.HttpLoggingInterceptor$Level
 *  org.apache.commons.lang.StringUtils
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.util.ReflectionUtils
 *  retrofit2.Call
 *  retrofit2.Converter$Factory
 *  retrofit2.Response
 *  retrofit2.Retrofit
 *  retrofit2.Retrofit$Builder
 *  retrofit2.converter.moshi.MoshiConverterFactory
 *  retrofit2.http.Field
 *  retrofit2.http.FormUrlEncoded
 *  retrofit2.http.GET
 *  retrofit2.http.POST
 *  retrofit2.http.Path
 *  retrofit2.http.Query
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squareup.moshi.Json;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.BasePersonAttributeDao;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;
import org.springframework.util.ReflectionUtils;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MicrosoftGraphPersonAttributeDao
extends BasePersonAttributeDao {
    private final IUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider();
    private boolean caseInsensitiveUsername;
    private String tenant;
    private String resource = "https://graph.microsoft.com/";
    private String scope;
    private String grantType = "client_credentials";
    private String clientId;
    private String clientSecret;
    private String properties;
    private String apiBaseUrl = "https://graph.microsoft.com/v1.0/";
    private String loginBaseUrl = "https://login.microsoftonline.com/%s/";
    private String domain;
    private String loggingLevel = "BASIC";

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getLoginBaseUrl() {
        return this.loginBaseUrl;
    }

    public void setLoginBaseUrl(String loginBaseUrl) {
        this.loginBaseUrl = loginBaseUrl;
    }

    public String getLoggingLevel() {
        return this.loggingLevel;
    }

    public void setLoggingLevel(String loggingLevel) {
        this.loggingLevel = loggingLevel;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTenant() {
        return this.tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getGrantType() {
        return this.grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public IUsernameAttributeProvider getUsernameAttributeProvider() {
        return this.usernameAttributeProvider;
    }

    public boolean isCaseInsensitiveUsername() {
        return this.caseInsensitiveUsername;
    }

    public void setCaseInsensitiveUsername(boolean caseInsensitiveUsername) {
        this.caseInsensitiveUsername = caseInsensitiveUsername;
    }

    public String getApiBaseUrl() {
        return this.apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public IPersonAttributes getPerson(String uid, IPersonAttributeDaoFilter filter) {
        try {
            Objects.requireNonNull(uid, "username cannot be null");
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.valueOf((String)this.loggingLevel.toUpperCase()));
            String token = this.getToken();
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request().newBuilder().header("Authorization", "Bearer " + token).build();
                return chain.proceed(request);
            }).addInterceptor((Interceptor)loggingInterceptor).build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(this.apiBaseUrl).addConverterFactory((Converter.Factory)MoshiConverterFactory.create()).client(client).build();
            GraphApiService service = (GraphApiService)retrofit.create(GraphApiService.class);
            String user = this.domain == null ? uid : uid + "@" + this.domain;
            Call<User> call = service.getUserByUserPrincipalName(user, StringUtils.defaultIfBlank((String)this.properties, (String)User.getDefaultFieldQuery().stream().collect(Collectors.joining(","))));
            Response r = call.execute();
            if (r.isSuccessful()) {
                User response = (User)r.body();
                Map<String, Object> attributes = response.buildAttributes();
                if (this.caseInsensitiveUsername) {
                    return new CaseInsensitiveNamedPersonImpl(uid, MultivaluedPersonAttributeUtils.stuffAttributesIntoListValues(attributes, filter));
                }
                return new NamedPersonImpl(uid, MultivaluedPersonAttributeUtils.stuffAttributesIntoListValues(attributes, filter));
            }
            throw new RuntimeException("error requesting token (" + r.code() + "): " + r.errorBody());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Set<IPersonAttributes> getPeople(Map<String, Object> query, IPersonAttributeDaoFilter filter) {
        return this.getPeopleWithMultivaluedAttributes(MultivaluedPersonAttributeUtils.stuffAttributesIntoListValues(query, filter), filter);
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        LinkedHashSet<IPersonAttributes> people = new LinkedHashSet<IPersonAttributes>();
        String username = this.usernameAttributeProvider.getUsernameFromQuery(query);
        IPersonAttributes person = this.getPerson(username, filter);
        if (person != null) {
            people.add(person);
        }
        return people;
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return Collections.emptySet();
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return Collections.emptySet();
    }

    private String getToken() throws Exception {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.valueOf((String)this.loggingLevel.toUpperCase()));
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor((Interceptor)loggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(String.format(this.loginBaseUrl, this.tenant)).addConverterFactory((Converter.Factory)MoshiConverterFactory.create()).client(client).build();
        GraphAuthApiService service = (GraphAuthApiService)retrofit.create(GraphAuthApiService.class);
        Response response = service.getOauth2Token(this.grantType, this.clientId, this.clientSecret, this.scope, this.resource).execute();
        if (response.isSuccessful()) {
            OAuthTokenInfo info = (OAuthTokenInfo)response.body();
            return info.accessToken;
        }
        ResponseBody errorBody = response.errorBody();
        throw new RuntimeException("error requesting token (" + response.code() + "): " + errorBody);
    }

    private static class OAuthTokenInfo
    implements Serializable {
        private static final long serialVersionUID = -8586825191767772463L;
        @Json(name="token_type")
        public String tokenType;
        @Json(name="scope")
        public String scope;
        @Json(name="expires_in")
        public int expiresIn;
        @Json(name="expires_on")
        public int expiresOn;
        @Json(name="not_before")
        public int notBefore;
        @Json(name="resource")
        public String resource;
        @Json(name="access_token")
        public String accessToken;

        private OAuthTokenInfo() {
        }
    }

    public static class User
    implements Serializable {
        private static final long serialVersionUID = 8497244140827305607L;
        public String userPrincipalName;
        public String id;
        public boolean accountEnabled;
        public String displayName;
        public String mail;
        public String jobTitle;
        public String officeLocation;
        public String preferredLanguage;
        public String mobilePhone;
        public String surname;
        public String givenName;
        public String passwordPolicies;
        public String preferredName;
        public List<String> businessPhones = new ArrayList<String>(0);
        public List<String> schools = new ArrayList<String>(0);
        public List<String> skills = new ArrayList<String>(0);
        private String postalCode;
        private String consentProvidedForMinor;
        private String aboutMe;
        private String streetAddress;
        private String userType;
        private String usageLocation;
        private String state;
        private String ageGroup;
        private String otherMails;
        private String city;
        private String country;
        private String countryName;
        private String department;
        private String employeeId;
        private String faxNumber;
        private String mailNickname;
        private String onPremisesSamAccountName;

        @JsonIgnore
        static String getFieldQuery() {
            ArrayList fields = new ArrayList();
            ReflectionUtils.doWithFields(User.class, field -> {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    fields.add(field.getName());
                }
            });
            return fields.stream().collect(Collectors.joining(","));
        }

        static List<String> getDefaultFieldQuery() {
            return Arrays.asList("businessPhones,displayName,givenName,id,jobTitle,mail,givenName,employeeId,mobilePhone,officeLocation,accountEnabledpreferredLanguage,surname,userPrincipalName");
        }

        @JsonIgnore
        private Map<String, Object> buildAttributes() {
            HashMap<String, Object> fields = new HashMap<String, Object>();
            ReflectionUtils.doWithFields(this.getClass(), field -> {
                field.setAccessible(true);
                fields.put(field.getName(), field.get(this));
            });
            return fields;
        }
    }

    private static interface GraphAuthApiService {
        @FormUrlEncoded
        @POST(value="oauth2/token")
        public Call<OAuthTokenInfo> getOauth2Token(@Field(value="grant_type") String var1, @Field(value="client_id") String var2, @Field(value="client_secret") String var3, @Field(value="scope") String var4, @Field(value="resource") String var5);
    }

    private static interface GraphApiService {
        @GET(value="users/{upn}")
        public Call<User> getUserByUserPrincipalName(@Path(value="upn") String var1, @Query(value="$select", encoded=true) String var2);
    }
}

