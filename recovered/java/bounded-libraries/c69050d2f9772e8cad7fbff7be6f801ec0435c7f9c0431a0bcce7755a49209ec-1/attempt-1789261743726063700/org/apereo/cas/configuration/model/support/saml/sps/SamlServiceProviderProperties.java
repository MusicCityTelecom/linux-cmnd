/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.model.TriStateBoolean
 */
package org.apereo.cas.configuration.model.support.saml.sps;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.saml.sps.AbstractSamlSPProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.apereo.cas.util.model.TriStateBoolean;

@RequiresModule(name="cas-server-support-saml-sp-integrations")
public class SamlServiceProviderProperties
implements Serializable {
    private static final long serialVersionUID = 8602328179113963081L;
    private ConcurSolutions concurSolutions = new ConcurSolutions();
    private Qualtrics qualtrics = new Qualtrics();
    private Emma emma = new Emma();
    private CrashPlan crashPlan = new CrashPlan();
    private SafariOnline safariOnline = new SafariOnline();
    private TopHat topHat = new TopHat();
    private DocuSign docuSign = new DocuSign();
    private PagerDuty pagerDuty = new PagerDuty();
    private Jira jira = new Jira();
    private PollEverywhere pollEverywhere = new PollEverywhere();
    private Gitlab gitlab = new Gitlab();
    private Dropbox dropbox = new Dropbox();
    private Workday workday = new Workday();
    private SAManage saManage = new SAManage();
    private Salesforce salesforce = new Salesforce();
    private ServiceNow serviceNow = new ServiceNow();
    private Box box = new Box();
    private NetPartner netPartner = new NetPartner();
    private Webex webex = new Webex();
    private Office365 office365 = new Office365();
    private InCommon inCommon = new InCommon();
    private Zoom zoom = new Zoom();
    private Evernote evernote = new Evernote();
    private Asana asana = new Asana();
    private Gartner gartner = new Gartner();
    private Tableau tableau = new Tableau();
    private WebAdvisor webAdvisor = new WebAdvisor();
    private OpenAthens openAthens = new OpenAthens();
    private ArcGIS arcGIS = new ArcGIS();
    private BenefitFocus benefitFocus = new BenefitFocus();
    private AdobeCloud adobeCloud = new AdobeCloud();
    private AcademicWorks academicWorks = new AcademicWorks();
    private EasyIep easyIep = new EasyIep();
    private InfiniteCampus infiniteCampus = new InfiniteCampus();
    private SecuringTheHuman sansSth = new SecuringTheHuman();
    private Slack slack = new Slack();
    private Zendesk zendesk = new Zendesk();
    private Bynder bynder = new Bynder();
    private Famis famis = new Famis();
    private SunshineStateEdResearchAlliance sserca = new SunshineStateEdResearchAlliance();
    private EverBridge everBridge = new EverBridge();
    private CherWell cherWell = new CherWell();
    private Egnyte egnyte = new Egnyte();
    private NewRelic newRelic = new NewRelic();
    private Yuja yuja = new Yuja();
    private Confluence confluence = new Confluence();
    private Zimbra zimbra = new Zimbra();
    private Symplicity symplicity = new Symplicity();
    private AppDynamics appDynamics = new AppDynamics();
    private Amazon amazon = new Amazon();
    private BlackBaud blackBaud = new BlackBaud();
    private GiveCampus giveCampus = new GiveCampus();
    private WarpWire warpWire = new WarpWire();
    private RocketChat rocketChat = new RocketChat();
    private ArmsSoftware armsSoftware = new ArmsSoftware();
    private AcademicHealthPlans academicHealthPlans = new AcademicHealthPlans();
    private NeoGov neoGov = new NeoGov();
    private CraniumCafe craniumCafe = new CraniumCafe();
    private CaliforniaCommunityColleges cccco = new CaliforniaCommunityColleges();

    @Generated
    public ConcurSolutions getConcurSolutions() {
        return this.concurSolutions;
    }

    @Generated
    public Qualtrics getQualtrics() {
        return this.qualtrics;
    }

    @Generated
    public Emma getEmma() {
        return this.emma;
    }

    @Generated
    public CrashPlan getCrashPlan() {
        return this.crashPlan;
    }

    @Generated
    public SafariOnline getSafariOnline() {
        return this.safariOnline;
    }

    @Generated
    public TopHat getTopHat() {
        return this.topHat;
    }

    @Generated
    public DocuSign getDocuSign() {
        return this.docuSign;
    }

    @Generated
    public PagerDuty getPagerDuty() {
        return this.pagerDuty;
    }

    @Generated
    public Jira getJira() {
        return this.jira;
    }

    @Generated
    public PollEverywhere getPollEverywhere() {
        return this.pollEverywhere;
    }

    @Generated
    public Gitlab getGitlab() {
        return this.gitlab;
    }

    @Generated
    public Dropbox getDropbox() {
        return this.dropbox;
    }

    @Generated
    public Workday getWorkday() {
        return this.workday;
    }

    @Generated
    public SAManage getSaManage() {
        return this.saManage;
    }

    @Generated
    public Salesforce getSalesforce() {
        return this.salesforce;
    }

    @Generated
    public ServiceNow getServiceNow() {
        return this.serviceNow;
    }

    @Generated
    public Box getBox() {
        return this.box;
    }

    @Generated
    public NetPartner getNetPartner() {
        return this.netPartner;
    }

    @Generated
    public Webex getWebex() {
        return this.webex;
    }

    @Generated
    public Office365 getOffice365() {
        return this.office365;
    }

    @Generated
    public InCommon getInCommon() {
        return this.inCommon;
    }

    @Generated
    public Zoom getZoom() {
        return this.zoom;
    }

    @Generated
    public Evernote getEvernote() {
        return this.evernote;
    }

    @Generated
    public Asana getAsana() {
        return this.asana;
    }

    @Generated
    public Gartner getGartner() {
        return this.gartner;
    }

    @Generated
    public Tableau getTableau() {
        return this.tableau;
    }

    @Generated
    public WebAdvisor getWebAdvisor() {
        return this.webAdvisor;
    }

    @Generated
    public OpenAthens getOpenAthens() {
        return this.openAthens;
    }

    @Generated
    public ArcGIS getArcGIS() {
        return this.arcGIS;
    }

    @Generated
    public BenefitFocus getBenefitFocus() {
        return this.benefitFocus;
    }

    @Generated
    public AdobeCloud getAdobeCloud() {
        return this.adobeCloud;
    }

    @Generated
    public AcademicWorks getAcademicWorks() {
        return this.academicWorks;
    }

    @Generated
    public EasyIep getEasyIep() {
        return this.easyIep;
    }

    @Generated
    public InfiniteCampus getInfiniteCampus() {
        return this.infiniteCampus;
    }

    @Generated
    public SecuringTheHuman getSansSth() {
        return this.sansSth;
    }

    @Generated
    public Slack getSlack() {
        return this.slack;
    }

    @Generated
    public Zendesk getZendesk() {
        return this.zendesk;
    }

    @Generated
    public Bynder getBynder() {
        return this.bynder;
    }

    @Generated
    public Famis getFamis() {
        return this.famis;
    }

    @Generated
    public SunshineStateEdResearchAlliance getSserca() {
        return this.sserca;
    }

    @Generated
    public EverBridge getEverBridge() {
        return this.everBridge;
    }

    @Generated
    public CherWell getCherWell() {
        return this.cherWell;
    }

    @Generated
    public Egnyte getEgnyte() {
        return this.egnyte;
    }

    @Generated
    public NewRelic getNewRelic() {
        return this.newRelic;
    }

    @Generated
    public Yuja getYuja() {
        return this.yuja;
    }

    @Generated
    public Confluence getConfluence() {
        return this.confluence;
    }

    @Generated
    public Zimbra getZimbra() {
        return this.zimbra;
    }

    @Generated
    public Symplicity getSymplicity() {
        return this.symplicity;
    }

    @Generated
    public AppDynamics getAppDynamics() {
        return this.appDynamics;
    }

    @Generated
    public Amazon getAmazon() {
        return this.amazon;
    }

    @Generated
    public BlackBaud getBlackBaud() {
        return this.blackBaud;
    }

    @Generated
    public GiveCampus getGiveCampus() {
        return this.giveCampus;
    }

    @Generated
    public WarpWire getWarpWire() {
        return this.warpWire;
    }

    @Generated
    public RocketChat getRocketChat() {
        return this.rocketChat;
    }

    @Generated
    public ArmsSoftware getArmsSoftware() {
        return this.armsSoftware;
    }

    @Generated
    public AcademicHealthPlans getAcademicHealthPlans() {
        return this.academicHealthPlans;
    }

    @Generated
    public NeoGov getNeoGov() {
        return this.neoGov;
    }

    @Generated
    public CraniumCafe getCraniumCafe() {
        return this.craniumCafe;
    }

    @Generated
    public CaliforniaCommunityColleges getCccco() {
        return this.cccco;
    }

    @Generated
    public SamlServiceProviderProperties setConcurSolutions(ConcurSolutions concurSolutions) {
        this.concurSolutions = concurSolutions;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setQualtrics(Qualtrics qualtrics) {
        this.qualtrics = qualtrics;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setEmma(Emma emma) {
        this.emma = emma;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setCrashPlan(CrashPlan crashPlan) {
        this.crashPlan = crashPlan;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSafariOnline(SafariOnline safariOnline) {
        this.safariOnline = safariOnline;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setTopHat(TopHat topHat) {
        this.topHat = topHat;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setDocuSign(DocuSign docuSign) {
        this.docuSign = docuSign;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setPagerDuty(PagerDuty pagerDuty) {
        this.pagerDuty = pagerDuty;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setJira(Jira jira) {
        this.jira = jira;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setPollEverywhere(PollEverywhere pollEverywhere) {
        this.pollEverywhere = pollEverywhere;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setGitlab(Gitlab gitlab) {
        this.gitlab = gitlab;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setDropbox(Dropbox dropbox) {
        this.dropbox = dropbox;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setWorkday(Workday workday) {
        this.workday = workday;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSaManage(SAManage saManage) {
        this.saManage = saManage;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSalesforce(Salesforce salesforce) {
        this.salesforce = salesforce;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setServiceNow(ServiceNow serviceNow) {
        this.serviceNow = serviceNow;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setBox(Box box) {
        this.box = box;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setNetPartner(NetPartner netPartner) {
        this.netPartner = netPartner;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setWebex(Webex webex) {
        this.webex = webex;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setOffice365(Office365 office365) {
        this.office365 = office365;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setInCommon(InCommon inCommon) {
        this.inCommon = inCommon;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setZoom(Zoom zoom) {
        this.zoom = zoom;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setEvernote(Evernote evernote) {
        this.evernote = evernote;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAsana(Asana asana) {
        this.asana = asana;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setGartner(Gartner gartner) {
        this.gartner = gartner;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setTableau(Tableau tableau) {
        this.tableau = tableau;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setWebAdvisor(WebAdvisor webAdvisor) {
        this.webAdvisor = webAdvisor;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setOpenAthens(OpenAthens openAthens) {
        this.openAthens = openAthens;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setArcGIS(ArcGIS arcGIS) {
        this.arcGIS = arcGIS;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setBenefitFocus(BenefitFocus benefitFocus) {
        this.benefitFocus = benefitFocus;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAdobeCloud(AdobeCloud adobeCloud) {
        this.adobeCloud = adobeCloud;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAcademicWorks(AcademicWorks academicWorks) {
        this.academicWorks = academicWorks;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setEasyIep(EasyIep easyIep) {
        this.easyIep = easyIep;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setInfiniteCampus(InfiniteCampus infiniteCampus) {
        this.infiniteCampus = infiniteCampus;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSansSth(SecuringTheHuman sansSth) {
        this.sansSth = sansSth;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSlack(Slack slack) {
        this.slack = slack;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setZendesk(Zendesk zendesk) {
        this.zendesk = zendesk;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setBynder(Bynder bynder) {
        this.bynder = bynder;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setFamis(Famis famis) {
        this.famis = famis;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSserca(SunshineStateEdResearchAlliance sserca) {
        this.sserca = sserca;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setEverBridge(EverBridge everBridge) {
        this.everBridge = everBridge;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setCherWell(CherWell cherWell) {
        this.cherWell = cherWell;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setEgnyte(Egnyte egnyte) {
        this.egnyte = egnyte;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setNewRelic(NewRelic newRelic) {
        this.newRelic = newRelic;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setYuja(Yuja yuja) {
        this.yuja = yuja;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setConfluence(Confluence confluence) {
        this.confluence = confluence;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setZimbra(Zimbra zimbra) {
        this.zimbra = zimbra;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setSymplicity(Symplicity symplicity) {
        this.symplicity = symplicity;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAppDynamics(AppDynamics appDynamics) {
        this.appDynamics = appDynamics;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAmazon(Amazon amazon) {
        this.amazon = amazon;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setBlackBaud(BlackBaud blackBaud) {
        this.blackBaud = blackBaud;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setGiveCampus(GiveCampus giveCampus) {
        this.giveCampus = giveCampus;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setWarpWire(WarpWire warpWire) {
        this.warpWire = warpWire;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setRocketChat(RocketChat rocketChat) {
        this.rocketChat = rocketChat;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setArmsSoftware(ArmsSoftware armsSoftware) {
        this.armsSoftware = armsSoftware;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setAcademicHealthPlans(AcademicHealthPlans academicHealthPlans) {
        this.academicHealthPlans = academicHealthPlans;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setNeoGov(NeoGov neoGov) {
        this.neoGov = neoGov;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setCraniumCafe(CraniumCafe craniumCafe) {
        this.craniumCafe = craniumCafe;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties setCccco(CaliforniaCommunityColleges cccco) {
        this.cccco = cccco;
        return this;
    }

    @Generated
    public SamlServiceProviderProperties() {
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Qualtrics
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Qualtrics() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName(), CommonAttributeNames.EMPLOYEE_NUMBER.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Emma
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Emma() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class TopHat
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public TopHat() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Zimbra
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Zimbra() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class PagerDuty
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public PagerDuty() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class SafariOnline
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public SafariOnline() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.EMPLOYEE_NUMBER.getAttributeName(), CommonAttributeNames.EDU_PERSON_AFFILIATION.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class DocuSign
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public DocuSign() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.EMPLOYEE_NUMBER.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class CrashPlan
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public CrashPlan() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Jira
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Jira() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.UID.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.DISPLAY_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Confluence
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Confluence() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.UID.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.DISPLAY_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class CaliforniaCommunityColleges
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public CaliforniaCommunityColleges() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.UID.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.COMMON_NAME.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRIMARY_AFFILIATION.getAttributeName(), CommonAttributeNames.EDU_PERSON_SCOPED_AFFILIATION.getAttributeName(), CommonAttributeNames.DISPLAY_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class CraniumCafe
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public CraniumCafe() {
            this.setEntityIds(List.of("https://my.craniumcafe.com/login/saml2"));
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName(), CommonAttributeNames.EDU_PERSON_SCOPED_AFFILIATION.getAttributeName(), CommonAttributeNames.STUDENT_ID.getAttributeName(), CommonAttributeNames.DISPLAY_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class NeoGov
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public NeoGov() {
            this.setEntityIds(List.of("https://login.neogov.com/"));
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.IMMUTABLE_ID.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class AcademicHealthPlans
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public AcademicHealthPlans() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.STUDENT_ID.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class ArmsSoftware
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public ArmsSoftware() {
            this.setEntityIds(List.of("https://sso.armssoftware.com/sp/shibboleth"));
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.UID.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class WarpWire
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public WarpWire() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.EMPLOYEE_NUMBER.getAttributeName(), CommonAttributeNames.EDU_PERSON_SCOPED_AFFILIATION.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class RocketChat
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public RocketChat() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.CN.getAttributeName(), CommonAttributeNames.USERNAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class GiveCampus
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public GiveCampus() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.SURNAME.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), CommonAttributeNames.DISPLAY_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class BlackBaud
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public BlackBaud() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.addAttributes(CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName(), CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class ConcurSolutions
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public ConcurSolutions() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.setNameIdAttribute(CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class PollEverywhere
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public PollEverywhere() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.setNameIdAttribute(CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Amazon
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Amazon() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(false);
            this.addAttributes("awsRoles", "awsRoleSessionName");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class AppDynamics
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public AppDynamics() {
            this.addAttributes("User.OpenIDName", "User.email", "User.fullName", "AccessControl", "Groups-Membership");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Gitlab
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Gitlab() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), "last_name", "first_name", "name");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Gartner
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6141931806328699054L;

        public Gartner() {
            this.addAttributes("urn:oid:2.5.4.42", "urn:oid:2.5.4.4", "urn:oid:0.9.2342.19200300.100.1.3");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class SunshineStateEdResearchAlliance
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -5558960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class EverBridge
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -5168960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Egnyte
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -3168760591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Symplicity
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -3178960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Yuja
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -1168960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class NewRelic
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -3268960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class CherWell
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -3168960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Bynder
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -3168960591734555088L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Zendesk
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -4668960591734555087L;

        public Zendesk() {
            this.setNameIdFormat("emailAddress");
            this.setNameIdAttribute("email");
            this.addAttributes("organization", "tags", "phone", "role");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Slack
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -1996859011579246804L;

        public Slack() {
            this.setNameIdFormat("persistent");
            this.addAttributes("User.Email", "User.Username", "first_name", "last_name");
            this.setNameIdAttribute("employeeId");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class SecuringTheHuman
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -1688194227471468248L;

        public SecuringTheHuman() {
            this.addAttributes(CommonAttributeNames.FIRST_NAME.getAttributeName(), CommonAttributeNames.LAST_NAME.getAttributeName(), CommonAttributeNames.EMAIL.getAttributeName(), "scopedUserId", "department", "reference");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class InfiniteCampus
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -9023417844664430533L;

        public InfiniteCampus() {
            this.addAttributes("employeeId");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class EasyIep
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 6177866628049579956L;

        public EasyIep() {
            this.addAttributes("employeeId");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class AcademicWorks
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 5855725238963607605L;

        public AcademicWorks() {
            this.addAttributes(CommonAttributeNames.DISPLAY_NAME.getAttributeName(), CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class AdobeCloud
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -5466434234795577247L;

        public AdobeCloud() {
            this.addAttributes(CommonAttributeNames.FIRST_NAME.getAttributeName(), CommonAttributeNames.LAST_NAME.getAttributeName(), CommonAttributeNames.EMAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class BenefitFocus
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6518570556068267724L;

        public BenefitFocus() {
            this.setNameIdAttribute("benefitFocusUniqueId");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class OpenAthens
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 7295249577313928465L;

        public OpenAthens() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Asana
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 6392492484052314295L;

        public Asana() {
            this.setNameIdAttribute(CommonAttributeNames.EMAIL.getAttributeName());
            this.setNameIdFormat("emailAddress");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Evernote
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -1333379518527897627L;

        public Evernote() {
            this.setNameIdAttribute(CommonAttributeNames.EMAIL.getAttributeName());
            this.setNameIdFormat("emailAddress");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class InCommon
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -6336757169059216490L;

        public InCommon() {
            this.addAttributes(CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class ArcGIS
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 2976006720801066953L;

        public ArcGIS() {
            this.setNameIdAttribute("arcNameId");
            this.addAttributes(CommonAttributeNames.MAIL.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName(), "arcNameId");
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Zoom
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -4877129302021248398L;

        public Zoom() {
            this.setNameIdAttribute(CommonAttributeNames.MAIL.getAttributeName());
            this.addAttributes(CommonAttributeNames.MAIL.getAttributeName(), CommonAttributeNames.SN.getAttributeName(), CommonAttributeNames.GIVEN_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Tableau
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -2426590644028989950L;

        public Tableau() {
            this.addAttributes(CommonAttributeNames.USERNAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Webex
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 1957066095836617091L;

        public Webex() {
            this.setNameIdAttribute(CommonAttributeNames.EMAIL.getAttributeName());
            this.addAttributes(CommonAttributeNames.FIRST_NAME.getAttributeName(), CommonAttributeNames.LAST_NAME.getAttributeName());
            this.setSignResponses(false);
            this.setSignAssertions(TriStateBoolean.TRUE);
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class WebAdvisor
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 8449304623099588610L;

        public WebAdvisor() {
            this.addAttributes(CommonAttributeNames.UID.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Office365
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 5878458463269060163L;

        public Office365() {
            this.setNameIdAttribute("objectGUID");
            this.addAttributes("IDPEmail", CommonAttributeNames.IMMUTABLE_ID.getAttributeName());
            this.setSignResponses(false);
            this.setSignAssertions(TriStateBoolean.TRUE);
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class NetPartner
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 5262806306575955633L;

        public NetPartner() {
            this.setNameIdAttribute(CommonAttributeNames.STUDENT_ID.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class ServiceNow
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 4329681021653966734L;

        public ServiceNow() {
            this.addAttributes(CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Salesforce
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 4685484530782109454L;

        public Salesforce() {
            this.addAttributes(CommonAttributeNames.MAIL.getAttributeName(), CommonAttributeNames.EDU_PERSON_PRINCIPAL_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Famis
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 4685484530782109454L;
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Workday
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = 3484810792914261584L;

        public Workday() {
            this.setSignAssertions(TriStateBoolean.TRUE);
            this.setSignResponses(true);
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class SAManage
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -8695176237527302883L;

        public SAManage() {
            this.setNameIdAttribute(CommonAttributeNames.MAIL.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Box
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -5320292115253509284L;

        public Box() {
            this.addAttributes(CommonAttributeNames.EMAIL.getAttributeName(), CommonAttributeNames.FIRST_NAME.getAttributeName(), CommonAttributeNames.LAST_NAME.getAttributeName());
        }
    }

    @RequiresModule(name="cas-server-support-saml-sp-integrations")
    public static class Dropbox
    extends AbstractSamlSPProperties {
        private static final long serialVersionUID = -8275173711355379058L;

        public Dropbox() {
            this.setNameIdAttribute(CommonAttributeNames.MAIL.getAttributeName());
        }
    }

    private static enum CommonAttributeNames {
        EDU_PERSON_PRINCIPAL_NAME("eduPersonPrincipalName"),
        EDU_PERSON_PRIMARY_AFFILIATION("eduPersonPrimaryAffiliation"),
        EDU_PERSON_AFFILIATION("eduPersonAffiliation"),
        EDU_PERSON_SCOPED_AFFILIATION("eduPersonScopedAffiliation"),
        GIVEN_NAME("givenName"),
        DISPLAY_NAME("displayName"),
        SURNAME("surname"),
        UID("uid"),
        COMMON_NAME("commonName"),
        USERNAME("username"),
        FIRST_NAME("firstName"),
        LAST_NAME("lastName"),
        SN("sn"),
        CN("cn"),
        MAIL("mail"),
        EMPLOYEE_NUMBER("employeeNumber"),
        STUDENT_ID("studentId"),
        IMMUTABLE_ID("ImmutableID"),
        EMAIL("email");

        private final String attributeName;

        private CommonAttributeNames(String name) {
            this.attributeName = name;
        }

        @Generated
        public String getAttributeName() {
            return this.attributeName;
        }
    }
}

