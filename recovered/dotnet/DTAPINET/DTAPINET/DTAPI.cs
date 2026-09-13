using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

[StructLayout(LayoutKind.Sequential, Size = 1)]
public struct DTAPI
{
	public const int VERSION_MAJOR = 6;

	public const int VERSION_MINOR = 2;

	public const int VERSION_BUGFIX = 0;

	public const int VERSION_BUILD = 219;

	public static DtCaps CAP_EMPTY;

	public static DtCaps CAP_C2X;

	public static DtCaps CAP_DP;

	public static DtCaps CAP_DTTV;

	public static DtCaps CAP_E;

	public static DtCaps CAP_J;

	public static DtCaps CAP_J2K;

	public static DtCaps CAP_MR;

	public static DtCaps CAP_MS;

	public static DtCaps CAP_MX;

	public static DtCaps CAP_RC;

	public static DtCaps CAP_RX;

	public static DtCaps CAP_SL;

	public static DtCaps CAP_SP;

	public static DtCaps CAP_SX;

	public static DtCaps CAP_SXNIC;

	public static DtCaps CAP_SY;

	public static DtCaps CAP_XP;

	public static DtCaps CAP_VR;

	public static DtCaps CAP_VRDGL;

	public static DtCaps CAP_T2X;

	public static DtCaps CAP_BW;

	public static DtCaps CAP_FAILSAFE;

	public static DtCaps CAP_FRACMODE;

	public static DtCaps CAP_GENLOCKED;

	public static DtCaps CAP_GENREF;

	public static DtCaps CAP_SWS2APSK;

	public static DtCaps CAP_ANTPWR;

	public static DtCaps CAP_LNB;

	public static DtCaps CAP_RX_ADV;

	public static DtCaps CAP_LBAND;

	public static DtCaps CAP_VHF;

	public static DtCaps CAP_UHF;

	public static DtCaps CAP_DISABLED;

	public static DtCaps CAP_INPUT;

	public static DtCaps CAP_OUTPUT;

	public static DtCaps CAP_SHAREDANT;

	public static DtCaps CAP_DBLBUF;

	public static DtCaps CAP_LOOPS2L3;

	public static DtCaps CAP_LOOPS2TS;

	public static DtCaps CAP_LOOPTHR;

	public static DtCaps CAP_ASIPOL;

	public static DtCaps CAP_HUFFMAN;

	public static DtCaps CAP_IPPAIR;

	public static DtCaps CAP_L3MODE;

	public static DtCaps CAP_MATRIX;

	public static DtCaps CAP_MATRIX2;

	public static DtCaps CAP_RAWASI;

	public static DtCaps CAP_SDI10BNBO;

	public static DtCaps CAP_SDITIME;

	public static DtCaps CAP_TIMESTAMP64;

	public static DtCaps CAP_TRPMODE;

	public static DtCaps CAP_TS;

	public static DtCaps CAP_TXONTIME;

	public static DtCaps CAP_3GSDI;

	public static DtCaps CAP_ASI;

	public static DtCaps CAP_DEMOD;

	public static DtCaps CAP_GPSTIME;

	public static DtCaps CAP_HDSDI;

	public static DtCaps CAP_IFADC;

	public static DtCaps CAP_IP;

	public static DtCaps CAP_MOD;

	public static DtCaps CAP_PHASENOISE;

	public static DtCaps CAP_RS422;

	public static DtCaps CAP_SDI;

	public static DtCaps CAP_SPI;

	public static DtCaps CAP_SPISDI;

	public static DtCaps CAP_1080P50;

	public static DtCaps CAP_1080P59_94;

	public static DtCaps CAP_1080P60;

	public static DtCaps CAP_1080I50;

	public static DtCaps CAP_1080I59_94;

	public static DtCaps CAP_1080I60;

	public static DtCaps CAP_1080P23_98;

	public static DtCaps CAP_1080P24;

	public static DtCaps CAP_1080P25;

	public static DtCaps CAP_1080P29_97;

	public static DtCaps CAP_1080P30;

	public static DtCaps CAP_720P23_98;

	public static DtCaps CAP_720P24;

	public static DtCaps CAP_720P25;

	public static DtCaps CAP_720P29_97;

	public static DtCaps CAP_720P30;

	public static DtCaps CAP_720P50;

	public static DtCaps CAP_720P59_94;

	public static DtCaps CAP_720P60;

	public static DtCaps CAP_525I59_94;

	public static DtCaps CAP_625I50;

	public static DtCaps CAP_SPI525I59_94;

	public static DtCaps CAP_SPI625I50;

	public static DtCaps CAP_TX_ATSC;

	public static DtCaps CAP_TX_ATSC3;

	public static DtCaps CAP_TX_CMMB;

	public static DtCaps CAP_TX_DAB;

	public static DtCaps CAP_TX_DTMB;

	public static DtCaps CAP_TX_DVBC2;

	public static DtCaps CAP_TX_DVBS;

	public static DtCaps CAP_TX_DVBS2;

	public static DtCaps CAP_TX_DVBS2X;

	public static DtCaps CAP_TX_DVBT;

	public static DtCaps CAP_TX_DVBT2;

	public static DtCaps CAP_TX_GOLD;

	public static DtCaps CAP_TX_IQ;

	public static DtCaps CAP_TX_ISDBS;

	public static DtCaps CAP_TX_ISDBT;

	public static DtCaps CAP_TX_MH;

	public static DtCaps CAP_TX_QAMA;

	public static DtCaps CAP_TX_QAMB;

	public static DtCaps CAP_TX_QAMC;

	public static DtCaps CAP_TX_SWMC;

	public static DtCaps CAP_TX_T2MI;

	public static DtCaps CAP_TX_T2SPLP;

	public static DtCaps CAP_ADJLVL;

	public static DtCaps CAP_CM;

	public static DtCaps CAP_CW;

	public static DtCaps CAP_DIGIQ;

	public static DtCaps CAP_DVBCID;

	public static DtCaps CAP_IF;

	public static DtCaps CAP_MUTE;

	public static DtCaps CAP_ROLLOFF;

	public static DtCaps CAP_S2APSK;

	public static DtCaps CAP_SNR;

	public static DtCaps CAP_TX_16MHZ;

	public static DtCaps CAP_TX_SFN;

	public static DtCaps CAP_RFCLKEXT;

	public static DtCaps CAP_RFCLKINT;

	public static DtCaps CAP_RX_ATSC;

	public static DtCaps CAP_RX_ATSC3;

	public static DtCaps CAP_RX_CMMB;

	public static DtCaps CAP_RX_DAB;

	public static DtCaps CAP_RX_DTMB;

	public static DtCaps CAP_RX_DVBC2;

	public static DtCaps CAP_RX_DVBS;

	public static DtCaps CAP_RX_DVBS2;

	public static DtCaps CAP_RX_DVBT;

	public static DtCaps CAP_RX_DVBT2;

	public static DtCaps CAP_RX_GOLD;

	public static DtCaps CAP_RX_IQ;

	public static DtCaps CAP_RX_ISDBS;

	public static DtCaps CAP_RX_ISDBT;

	public static DtCaps CAP_RX_MH;

	public static DtCaps CAP_RX_QAMA;

	public static DtCaps CAP_RX_QAMB;

	public static DtCaps CAP_RX_QAMC;

	public static DtCaps CAP_RX_T2MI;

	public static DtCaps CAP_SPICLKEXT;

	public static DtCaps CAP_SPICLKINT;

	public static DtCaps CAP_SPIFIXEDCLK;

	public static DtCaps CAP_SPIDVBMODE;

	public static DtCaps CAP_SPISER8B;

	public static DtCaps CAP_SPISER10B;

	public static DtCaps CAP_SPILVDS1;

	public static DtCaps CAP_SPILVDS2;

	public static DtCaps CAP_SPILVTTL;

	public static DtCaps CAP_EXTTSRATE;

	public static DtCaps CAP_EXTRATIO;

	public static DtCaps CAP_INTTSRATE;

	public static DtCaps CAP_LOCK2INP;

	public const int CMMB_BW_2MHZ = 0;

	public const int CMMB_BW_8MHZ = 1;

	public const int CM_MAX_PATHS = 32;

	public const int CAT_ALL = -1;

	public const int CAT_PCI = 0;

	public const int CAT_USB = 1;

	public const int CAT_NW = 2;

	public const int CAT_IP = 3;

	public const int CAT_NIC = 4;

	public const int CAT_NWAP = 5;

	public const int EVENT_TYPE_ADD = 1;

	public const int EVENT_TYPE_REMOVE = 2;

	public const int EVENT_TYPE_POWER = 4;

	public const int EVENT_TYPE_TEST = int.MinValue;

	public const int EVENT_IP_CHANGED = 16777216;

	public const int EVENT_ADMINST_CHANGED = 33554432;

	public const int EVENT_TYPE_ALL = -1;

	public const int EVENT_VALUE1_POWER_UP = 1;

	public const int EVENT_VALUE1_POWER_DOWN = 2;

	public const int EVENT_VALUE2_XXX = 1;

	public const int MAX_NUM_COEFFS = 64;

	public const int CHAN_DISABLED = 0;

	public const int CHAN_INPUT = 1;

	public const int CHAN_OUTPUT = 2;

	public const int CHAN_DBLBUF = 4;

	public const int CHAN_LOOPTHR = 8;

	public const int IOCONFIG_IODIR = 0;

	public const int IOCONFIG_IOSTD = 1;

	public const int IOCONFIG_RFCLKSEL = 4;

	public const int IOCONFIG_SPICLKSEL = 5;

	public const int IOCONFIG_SPIMODE = 6;

	public const int IOCONFIG_SPISTD = 7;

	public const int IOCONFIG_TSRATESEL = 8;

	public const int IOCONFIG_BW = 11;

	public const int IOCONFIG_FAILSAFE = 13;

	public const int IOCONFIG_FRACMODE = 14;

	public const int IOCONFIG_GENLOCKED = 15;

	public const int IOCONFIG_GENREF = 16;

	public const int IOCONFIG_SWS2APSK = 17;

	public const int IOCONFIG_TRUE = 18;

	public const int IOCONFIG_FALSE = 19;

	public const int IOCONFIG_DISABLED = 20;

	public const int IOCONFIG_INPUT = 21;

	public const int IOCONFIG_OUTPUT = 25;

	public const int IOCONFIG_SHAREDANT = 26;

	public const int IOCONFIG_DBLBUF = 27;

	public const int IOCONFIG_LOOPS2L3 = 28;

	public const int IOCONFIG_LOOPS2TS = 29;

	public const int IOCONFIG_LOOPTHR = 30;

	public const int IOCONFIG_3GSDI = 32;

	public const int IOCONFIG_ASI = 34;

	public const int IOCONFIG_DEMOD = 37;

	public const int IOCONFIG_GPSTIME = 38;

	public const int IOCONFIG_HDSDI = 40;

	public const int IOCONFIG_IFADC = 41;

	public const int IOCONFIG_IP = 42;

	public const int IOCONFIG_MOD = 43;

	public const int IOCONFIG_PHASENOISE = 44;

	public const int IOCONFIG_RS422 = 45;

	public const int IOCONFIG_SDI = 47;

	public const int IOCONFIG_SPI = 48;

	public const int IOCONFIG_SPISDI = 49;

	public const int IOCONFIG_1080P50 = 56;

	public const int IOCONFIG_1080P59_94 = 58;

	public const int IOCONFIG_1080P60 = 60;

	public const int IOCONFIG_1080I50 = 67;

	public const int IOCONFIG_1080I59_94 = 68;

	public const int IOCONFIG_1080I60 = 69;

	public const int IOCONFIG_1080P23_98 = 70;

	public const int IOCONFIG_1080P24 = 71;

	public const int IOCONFIG_1080P25 = 72;

	public const int IOCONFIG_1080P29_97 = 73;

	public const int IOCONFIG_1080P30 = 74;

	public const int IOCONFIG_720P23_98 = 80;

	public const int IOCONFIG_720P24 = 81;

	public const int IOCONFIG_720P25 = 82;

	public const int IOCONFIG_720P29_97 = 83;

	public const int IOCONFIG_720P30 = 84;

	public const int IOCONFIG_720P59_94 = 86;

	public const int IOCONFIG_720P60 = 87;

	public const int IOCONFIG_525I59_94 = 88;

	public const int IOCONFIG_625I50 = 89;

	public const int IOCONFIG_SPI525I59_94 = 90;

	public const int IOCONFIG_SPI625I50 = 91;

	public const int IOCONFIG_RFCLKEXT = 96;

	public const int IOCONFIG_RFCLKINT = 97;

	public const int IOCONFIG_SPICLKEXT = 98;

	public const int IOCONFIG_SPICLKINT = 99;

	public const int IOCONFIG_SPIFIXEDCLK = 100;

	public const int IOCONFIG_SPIDVBMODE = 101;

	public const int IOCONFIG_SPISER8B = 102;

	public const int IOCONFIG_SPISER10B = 103;

	public const int IOCONFIG_SPILVDS1 = 104;

	public const int IOCONFIG_SPILVDS2 = 105;

	public const int IOCONFIG_SPILVTTL = 106;

	public const int IOCONFIG_EXTTSRATE = 107;

	public const int IOCONFIG_EXTRATIO = 108;

	public const int IOCONFIG_INTTSRATE = 109;

	public const int IOCONFIG_LOCK2INP = 110;

	public const int ISDBS_SLOTS_PER_FRAME = 48;

	public const int ISDBS_MODCOD_BPSK_1_2 = 1;

	public const int ISDBS_MODCOD_QPSK_1_2 = 2;

	public const int ISDBS_MODCOD_QPSK_2_3 = 3;

	public const int ISDBS_MODCOD_QPSK_3_4 = 4;

	public const int ISDBS_MODCOD_QPSK_5_6 = 5;

	public const int ISDBS_MODCOD_QPSK_7_8 = 6;

	public const int ISDBS_MODCOD_8PSK_2_3 = 7;

	public const int ISDBS_MODCOD_NOT_ALLOC = 15;

	public const int ISDBT_NUM_TS_MAX = 14;

	public const int ISDBT_LAYER_NONE = -1;

	public const int ISDBT_LAYER_AUTO = -2;

	public const int ISDBT_LAYER_A = 1;

	public const int ISDBT_LAYER_B = 2;

	public const int ISDBT_LAYER_C = 4;

	public const int ISDBT_BTYPE_TV = 0;

	public const int ISDBT_BTYPE_RAD1 = 1;

	public const int ISDBT_BTYPE_RAD3 = 2;

	public const int ISDBT_GUARD_1_32 = 0;

	public const int ISDBT_GUARD_1_16 = 1;

	public const int ISDBT_GUARD_1_8 = 2;

	public const int ISDBT_GUARD_1_4 = 3;

	public const int ISDBT_MOD_DQPSK = 0;

	public const int ISDBT_MOD_QPSK = 1;

	public const int ISDBT_MOD_QAM16 = 2;

	public const int ISDBT_MOD_QAM64 = 3;

	public const int ISDBT_RATE_1_2 = 0;

	public const int ISDBT_RATE_2_3 = 1;

	public const int ISDBT_RATE_3_4 = 2;

	public const int ISDBT_RATE_5_6 = 3;

	public const int ISDBT_RATE_7_8 = 4;

	public const int ISDBT_SEGM_1 = 1;

	public const int ISDBT_SEGM_3 = 3;

	public const int ISDBT_SEGM_13 = 13;

	public const int ISDBT_SEGM_MSK = 15;

	public const int ISDBT_BW_5MHZ = 16;

	public const int ISDBT_BW_6MHZ = 32;

	public const int ISDBT_BW_7MHZ = 48;

	public const int ISDBT_BW_8MHZ = 64;

	public const int ISDBT_BW_MSK = 240;

	public const int ISDBT_SRATE_1_1 = 256;

	public const int ISDBT_SRATE_1_2 = 512;

	public const int ISDBT_SRATE_1_4 = 768;

	public const int ISDBT_SRATE_1_8 = 1024;

	public const int ISDBT_SRATE_27_32 = 1280;

	public const int ISDBT_SRATE_MSK = 3840;

	public const int ISDBT_SUBCH_MSK = 258048;

	public const int ISDBT_SUBCH_SHIFT = 12;

	public const int ISDBT_OK = 0;

	public const int ISDBT_E_BTYPE = 1;

	public const int ISDBT_E_NSEGM = 2;

	public const int ISDBT_E_PARTIAL = 3;

	public const int ISDBT_E_NOT_FILLED = 4;

	public const int ISDBT_E_SUBCHANNEL = 5;

	public const int ISDBT_E_SRATE = 6;

	public const int ISDBT_E_BANDWIDTH = 7;

	public const int ISDBT_E_MODE = 8;

	public const int ISDBT_E_GUARD = 9;

	public const int MAX_OUTPUTS = 16;

	public const int PAR_DEMOD_THREADS = 1;

	public const int PAR_DEMOD_LDPC_MAX = 2;

	public const int PAR_DEMOD_LDPC_AVG = 3;

	public const int PAR_DEMOD_ATSC_FFE_TAPS = 4;

	public const int PAR_DEMOD_ATSC_DFE_TAPS = 5;

	public const int PAR_DEMOD_ATSC_CLKSYNC_DELAY = 6;

	public const int PAR_DEMOD_MER_ENA = 7;

	public const int PAR_DEMOD_QAM_SPECINV = 8;

	public const int PAR_UNDEFINED = 0;

	public const int PAR_UNSUP_INTITEM = int.MinValue;

	public const int PAR_UNSUP_UINTITEM = -1;

	public const int STAT_ATSC3_L1DATA = 781;

	public const int STAT_ATSC3_TXID_INFO = 783;

	public const int STAT_BADPCKCNT = 3;

	public const int STAT_CNR = 261;

	public const int STAT_ATSC3_L1B_ERR = 31;

	public const int STAT_ATSC3_L1D_ERR = 32;

	public const int STAT_DVBC2_DSLICEDISC = 16;

	public const int STAT_DVBC2_L1HDR_ERR = 14;

	public const int STAT_DVBC2_L1P2_ERR = 15;

	public const int STAT_DVBT2_L1POST_ERR = 13;

	public const int STAT_DVBT2_L1PRE_ERR = 12;

	public const int STAT_EBN0 = 273;

	public const int STAT_ESN0 = 272;

	public const int STAT_LINKMARGIN = 271;

	public const int STAT_MER = 262;

	public const int STAT_MOD_SAT = 4;

	public const int STAT_RELOCKCNT = 10;

	public const int STAT_RFLVL_CHAN = 5;

	public const int STAT_RFLVL_NARROW_QS = 22;

	public const int STAT_RFLVL_CHAN_QS = 21;

	public const int STAT_RFLVL_NARROW = 6;

	public const int STAT_RS = 8;

	public const int STAT_SNR = 263;

	public const int STAT_T2MI_OVFS = 11;

	public const int STAT_TEMP_TUNER = 9;

	public const int STAT_LOCK_PERC = 33;

	public const int STAT_MER_PLHEADERS = 34;

	public const int STAT_BER_POSTBCH = 256;

	public const int STAT_BER_POSTLDPC = 257;

	public const int STAT_BER_POSTVIT = 258;

	public const int STAT_BER_PREBCH = 269;

	public const int STAT_BER_PRELDPC = 270;

	public const int STAT_BER_PRERS = 259;

	public const int STAT_BER_PREVIT = 260;

	public const int STAT_FER_POSTBCH = 278;

	public const int STAT_FER_POSTLDPC = 279;

	public const int STAT_FREQ_SHIFT = 267;

	public const int STAT_OCCUPIEDBW = 274;

	public const int STAT_PER = 264;

	public const int STAT_ROLLOFF = 275;

	public const int STAT_SAMPRATE_OFFSET = 268;

	public const int STAT_CARRIER_LOCK = 513;

	public const int STAT_FEC_LOCK = 514;

	public const int STAT_LOCK = 512;

	public const int STAT_STREAM_LOCK = 519;

	public const int STAT_PACKET_LOCK = 515;

	public const int STAT_SPECTRUMINV = 517;

	public const int STAT_VIT_LOCK = 516;

	public const int STAT_PLHEADER_LOCK = 518;

	public const int STAT_DAB_ENSEM_INFO = 776;

	public const int STAT_DAB_TXID_INFO = 780;

	public const int STAT_DVBC2_L1P2DATA = 768;

	public const int STAT_DVBC2_PLPSIGDATA = 769;

	public const int STAT_DVBS2_ISI = 784;

	public const int STAT_DVBS2_ISI_SIGDATA = 785;

	public const int STAT_DVBT_TPS_INFO = 779;

	public const int STAT_DVBT2_L1DATA = 770;

	public const int STAT_DVBT2_TXID_INFO = 782;

	public const int STAT_ISDBT_PARSDATA = 771;

	public const int STAT_LDPC_STATS = 772;

	public const int STAT_MA_DATA = 773;

	public const int STAT_MA_STATS = 774;

	public const int STAT_PLP_BLOCKS = 775;

	public const int STAT_RSDEC_STATS = 778;

	public const int STAT_VITDEC_STATS = 777;

	public const int STAT_AGC1 = 1;

	public const int STAT_AGC2 = 2;

	public const int STAT_RFLVL_UNCALIB = 7;

	public const int STAT_RFLVL_UNCALIB_DBM = 266;

	public const int STAT_SYNTAX_ERR_CNT = 276;

	public const int STAT_OVERFLOW_CNT = 277;

	public const int STAT_UNDEFINED = 0;

	public const int STAT_UNSUP_INTITEM = int.MinValue;

	public const int STAT_UNSUP_UINTITEM = -1;

	public const int STAT_IDXTRA_ISI_OVERALL = -1;

	public const int DEMOD_OFDM = 0;

	public const int DEMOD_QAM = 1;

	public const int ATTR_LEVEL_MAX = 1;

	public const int ATTR_LEVEL_RANGE = 2;

	public const int ATTR_LEVEL_STEPSIZE = 3;

	public const int ATTR_RFFREQ_ABSMAX = 4;

	public const int ATTR_RFFREQ_ABSMIN = 5;

	public const int ATTR_RFFREQ_MAX = 6;

	public const int ATTR_RFFREQ_MIN = 7;

	public const int ATTR_SAMPRHW_ABSMAX = 8;

	public const int ATTR_SAMPRHW_ABSMIN = 9;

	public const int ATTR_SAMPRHW_HARDLIM = 10;

	public const int ATTR_SAMPRHW_MAX = 11;

	public const int ATTR_SAMPRHW_MIN = 12;

	public const int ATTR_SAMPRATE_ABSMAX = 13;

	public const int ATTR_SAMPRATE_ABSMIN = 14;

	public const int ATTR_SAMPRATE_MAX = 15;

	public const int ATTR_SAMPRATE_MIN = 16;

	public const int ATTR_NUM_FANS = 17;

	public const int ATTR_PCIE_REQ_BW = 18;

	public const int ATTR_PCIE_AVAIL_BW = 19;

	public const int SCANORDER_ORIG = 0;

	public const int SCANORDER_SN = 1;

	public const int DVC2STR_TYPE_NMB = 0;

	public const int DVC2STR_TYPE_AND_LOC = 1;

	public const int DVC2STR_SN = 2;

	public const int HWF2STR_TYPE_NMB = 0;

	public const int HWF2STR_TYPE_AND_PORT = 1;

	public const int HWF2STR_TYPE_AND_LOC = 2;

	public const int HWF2STR_ITF_TYPE = 3;

	public const int HWF2STR_ITF_TYPE_SHORT = 4;

	public const int HWF2STR_TYPE_AND_PORT2 = 5;

	public const int HWF2STR_SN = 6;

	public const int GPS_1PPS_SYNC = 1;

	public const int GPS_10MHZ_SYNC = 2;

	public const int GPS_1PPS_ERROR = 1;

	public const int DTAPLUS_STATUS_OFF = 0;

	public const int DTAPLUS_STATUS_ON = 1;

	public const int DTAPLUS_STATUS_ATTN_FOLLOW_UP = 2;

	public const int DTAPLUS_STATUS_ATTN_FOLLOW_DOWN = 3;

	public const int DTAPLUS_STATUS_DAC_FOLLOW_UP = 4;

	public const int DTAPLUS_STATUS_DAC_FOLLOW_DOWN = 5;

	public const int DTAPLUS_STATUS_HOLD = 6;

	public const int DTAPLUS_STATUS_NO_SIGNAL = 7;

	public const int DTAPLUS_STATUS_OVER_POWER = 8;

	public const int DTAPLUS_TEMP_CONTROL_OFF = 0;

	public const int DTAPLUS_TEMP_CONTROL_FAN_ON = 1;

	public const int DTAPLUS_TEMP_CONTROL_HEATER_ON = 2;

	public const int FEC_DISABLE = 0;

	public const int FEC_2D = 1;

	public const int FEC_2D_M1 = 1;

	public const int FEC_2D_M2 = 2;

	public const int FEC_2D_M1_B = 3;

	public const int FEC_2D_M2_B = 4;

	public const int IP_V4 = 0;

	public const int IP_V6 = 1;

	public const int IP_TX_MANSRCPORT = 16;

	public const int IP_NORMAL = 0;

	public const int IP_TX_2022_7 = 1;

	public const int IP_RX_2022_7 = 2;

	public const int IP_PROF_NOT_DEFINED = 0;

	public const int IP_USER_DEFINED = 1;

	public const int IP_LBR_LOW_SKEW = 2;

	public const int IP_LBR_MODERATE_SKEW = 3;

	public const int IP_LBR_HIGH_SKEW = 4;

	public const int IP_SBR_LOW_SKEW = 5;

	public const int IP_SBR_MODERATE_SKEW = 6;

	public const int IP_SBR_HIGH_SKEW = 7;

	public const int IP_HBR_LOW_SKEW = 8;

	public const int IP_HBR_MODERATE_SKEW = 9;

	public const int IP_HBR_HIGH_SKEW = 10;

	public const int NWSPEED_AUTO = 0;

	public const int NWSPEED_NOLIN = 0;

	public const int NWSPEED_10MB_HALF = 1;

	public const int NWSPEED_10MB_FULL = 2;

	public const int NWSPEED_100MB_HALF = 3;

	public const int NWSPEED_100MB_FULL = 4;

	public const int NWSPEED_1GB_MASTER = 5;

	public const int NWSPEED_1GB_SLAVE = 6;

	public const int PROTO_UDP = 0;

	public const int PROTO_RTP = 1;

	public const int PROTO_AUTO = 2;

	public const int PROTO_UNKN = 2;

	public const int UCODE_NOT_LOADED = 0;

	public const int UCODE_LOADING = 1;

	public const int UCODE_LOADED = 2;

	public const long EV_TUNE_FREQ_CHANGED = 1L;

	public const long EV_TUNE_PARS_CHANGED = 2L;

	public const int ERRORSTATS_BER = 0;

	public const int ERRORSTATS_RS = 1;

	public const int NOT_SUPPORTED = -1;

	public const int ASIINV_NORMAL = 0;

	public const int ASIINV_INVERT = 1;

	public const int ASI_NOLOCK = 0;

	public const int ASI_INLOCK = 1;

	public const int GENLOCK_NOLOCK = 0;

	public const int GENLOCK_INLOCK = 1;

	public const int CLKDET_FAIL = 0;

	public const int CLKDET_OK = 1;

	public const int INPRATE_LOW = 0;

	public const int INPRATE_OK = 1;

	public const int NUMINV_NONE = 0;

	public const int NUMINV_16 = 1;

	public const int NUMINV_OTHER = 2;

	public const int PCKSIZE_INV = 0;

	public const int PCKSIZE_188 = 2;

	public const int PCKSIZE_204 = 3;

	public const int SDIMODE_INV = 0;

	public const int SDIMODE_525 = 1;

	public const int SDIMODE_625 = 2;

	public const int RXCTRL_IDLE = 0;

	public const int RXCTRL_RCV = 1;

	public const int RXMODE_TS = 16;

	public const int RXMODE_ST188 = 17;

	public const int RXMODE_ST204 = 18;

	public const int RXMODE_STMP2 = 19;

	public const int RXMODE_STRAW = 20;

	public const int RXMODE_STL3 = 21;

	public const int RXMODE_STL3FULL = 22;

	public const int RXMODE_IPRAW = 23;

	public const int RXMODE_RAWASI = 24;

	public const int RXMODE_STTRP = 25;

	public const int RXMODE_STL3ALL = 26;

	public const int RXMODE_TS_MASK = 31;

	public const int RXMODE_TIMESTAMP32 = 16777216;

	public const int RXMODE_TIMESTAMP64 = 33554432;

	public const int RXMODE_TIMESTAMP_TOD = 67108864;

	public const int RXMODE_SDI = 4096;

	public const int RXMODE_SDI_FULL = 4352;

	public const int RXMODE_SDI_ACTVID = 4608;

	public const int RXMODE_SDI_RAWDMA = 5120;

	public const int RXMODE_SDI_MASK = 7936;

	public const int RXMODE_SDI_HUFFMAN = 8192;

	public const int RXMODE_SDI_10B = 16384;

	public const int DEMOD_FECLOCK_FAIL = 0;

	public const int DEMOD_FECLOCK_OK = 1;

	public const int DEMOD_RCVLOCK_FAIL = 0;

	public const int DEMOD_RCVLOCK_OK = 1;

	public const int BAND_BROADCAST_ONAIR = 1;

	public const int BAND_FCC_CABLE = 2;

	public const int BAND_IRC = 3;

	public const int BAND_HRC = 4;

	public const int RFLVL_CHANNEL = 0;

	public const int RFLVL_NARROWBAND = 1;

	public const int ADCCLK_OFF = 0;

	public const int ADCCLK_20M647 = 20647059;

	public const int ADCCLK_13M5 = 13500000;

	public const int ADCCLK_27M = 27000000;

	public const int LNB_13V = 0;

	public const int LNB_18V = 1;

	public const int LNB_14V = 2;

	public const int LNB_19V = 3;

	public const int LNB_BURST_A = 0;

	public const int LNB_BURST_B = 1;

	public const int INSTANT_DETACH = 1;

	public const int WAIT_UNTIL_SENT = 2;

	public const int EQUALISER_OFF = 0;

	public const int EQUALISER_ON = 1;

	public const int LED_OFF = 0;

	public const int LED_GREEN = 1;

	public const int LED_RED = 2;

	public const int LED_YELLOW = 3;

	public const int LED_HARDWARE = 5;

	public const int NOISE_DISABLED = 0;

	public const int NOISE_WNG_HW = 1;

	public const int NOISE_UNIFORM_HW = 1;

	public const int NOISE_GAUSSIAN_HW = 2;

	public const int POLARITY_AUTO = 0;

	public const int POLARITY_NORMAL = 2;

	public const int POLARITY_INVERT = 3;

	public const int POWER_OFF = 0;

	public const int POWER_ON = 1;

	public const int FIFO_RESET = 0;

	public const int FULL_RESET = 1;

	public const int RFPLL_LOCK1 = 1;

	public const int RFPLL_LOCK2 = 2;

	public const int RFPLL_LOCK3 = 4;

	public const int RX_FIFO_OVF = 2;

	public const int RX_SYNC_ERR = 4;

	public const int RX_RATE_OVF = 8;

	public const int RX_TARGET_ERR = 16;

	public const int RX_LINK_ERR = 64;

	public const int RX_DATA_ERR = 128;

	public const int RX_DRV_BUF_OVF = 256;

	public const int RX_SYNTAX_ERR = 512;

	public const int SFN_IN_SYNC = 1;

	public const int SFN_TOO_EARLY_ERR = 1;

	public const int SFN_TOO_LATE_ERR = 2;

	public const int SFN_ABSTIME_ERR = 4;

	public const int SFN_DISCTIME_ERR = 8;

	public const int SFN_NOTIME_ERR = 16;

	public const int SFN_START_ERR = 32;

	public const int SFN_MODE_DISABLED = 0;

	public const int SFN_MODE_AT_1PPS = 1;

	public const int SFN_MODE_IQPCK = 2;

	public const int SFN_MODE_DVBT_MIP = 3;

	public const int SFN_MODE_T2MI = 4;

	public const int TX_FIFO_UFL = 2;

	public const int TX_SYNC_ERR = 4;

	public const int TX_READBACK_ERR = 8;

	public const int TX_TARGET_ERR = 16;

	public const int TX_MUX_OVF = 32;

	public const int TX_FIFO_OVF = 32;

	public const int TX_LINK_ERR = 64;

	public const int TX_DATA_ERR = 128;

	public const int TX_CPU_UFL = 256;

	public const int TX_DMA_UFL = 512;

	public const int NO_CONNECTION = 0;

	public const int DVB_SPI_SINK = 1;

	public const int DVB_SPI_SOURCE = 1;

	public const int TARGET_PRESENT = 2;

	public const int TARGET_UNKNOWN = 3;

	public const int TXCTRL_IDLE = 1;

	public const int TXCTRL_HOLD = 2;

	public const int TXCTRL_SEND = 3;

	public const int TXMODE_TS = 16;

	public const int TXMODE_188 = 17;

	public const int TXMODE_192 = 18;

	public const int TXMODE_204 = 19;

	public const int TXMODE_ADD16 = 20;

	public const int TXMODE_MIN16 = 21;

	public const int TXMODE_IPRAW = 22;

	public const int TXMODE_RAW = 23;

	public const int TXMODE_RAWASI = 24;

	public const int TXMODE_TS_MASK = 31;

	public const int TXMODE_BURST = 32;

	public const int TXMODE_TXONTIME = 64;

	public const int TXMODE_SDI = 4096;

	public const int TXMODE_SDI_FULL = 4352;

	public const int TXMODE_SDI_ACTVID = 4608;

	public const int TXMODE_SDI_MASK = 7936;

	public const int TXMODE_SDI_HUFFMAN = 8192;

	public const int TXMODE_SDI_10B = 16384;

	public const int TXSTUFF_MODE_OFF = 0;

	public const int TXSTUFF_MODE_ON = 1;

	public const int TXPOL_NORMAL = 0;

	public const int TXPOL_INVERTED = 1;

	public const int UPCONV_MODE = 0;

	public const int UPCONV_MODE_MSK = 15;

	public const int UPCONV_NORMAL = 0;

	public const int UPCONV_MUTE = 1;

	public const int UPCONV_CW = 2;

	public const int UPCONV_CWI = 3;

	public const int UPCONV_CWQ = 4;

	public const int UPCONV_SPECINV = 256;

	public const int USB_FULL_SPEED = 0;

	public const int USB_HIGH_SPEED = 1;

	public const int USB_SUPER_SPEED = 2;

	public const int PCIE_GEN_UNKNOWN = 0;

	public const int PCIE_GEN1 = 1;

	public const int PCIE_GEN2 = 2;

	public const int PCIE_GEN3 = 3;

	public const int MOD_DVBS_QPSK = 0;

	public const int MOD_DVBS_BPSK = 1;

	public const int MOD_QAM4 = 3;

	public const int MOD_QAM16 = 4;

	public const int MOD_QAM32 = 5;

	public const int MOD_QAM64 = 6;

	public const int MOD_QAM128 = 7;

	public const int MOD_QAM256 = 8;

	public const int MOD_DVBT = 9;

	public const int MOD_ATSC = 10;

	public const int MOD_ATSC3 = 69;

	public const int MOD_DVBT2 = 11;

	public const int MOD_ISDBT = 12;

	public const int MOD_ISDBS = 13;

	public const int MOD_ISDBS3 = 70;

	public const int MOD_IQDIRECT = 15;

	public const int MOD_IQ_2131 = 16;

	public const int MOD_DVBS2_QPSK = 32;

	public const int MOD_DVBS2_8PSK = 33;

	public const int MOD_DVBS2_16APSK = 34;

	public const int MOD_DVBS2_32APSK = 35;

	public const int MOD_DMBTH = 48;

	public const int MOD_ADTBT = 49;

	public const int MOD_CMMB = 50;

	public const int MOD_T2MI = 51;

	public const int MOD_DVBC2 = 52;

	public const int MOD_DAB = 53;

	public const int MOD_QAM_AUTO = 54;

	public const int MOD_ATSC_MH = 55;

	public const int MOD_ISDBTMM = 56;

	public const int MOD_S2X_QPSK_VLSNR = 57;

	public const int MOD_S2X_BPSK_VLSNR = 58;

	public const int MOD_S2X_BPSK_S_VLSNR = 59;

	public const int MOD_S2X_8APSK_L = 60;

	public const int MOD_S2X_16APSK_L = 61;

	public const int MOD_S2X_32APSK_L = 62;

	public const int MOD_S2X_64APSK = 63;

	public const int MOD_S2X_64APSK_L = 64;

	public const int MOD_S2X_128APSK = 65;

	public const int MOD_S2X_256APSK = 66;

	public const int MOD_S2X_256APSK_L = 67;

	public const int MOD_DVBS2X_L3 = 68;

	public const int MOD_TYPE_AUTO = -1;

	public const int MOD_TYPE_UNK = -1;

	public const int MOD_SYMRATE_AUTO = -1;

	public const int MOD_SYMRATE_UNK = -1;

	public const int MOD_ATSC_VSB8 = 0;

	public const int MOD_ATSC_VSB16 = 1;

	public const int MOD_ATSC_VSB_AUTO = 3;

	public const int MOD_ATSC_VSB_UNK = 3;

	public const int MOD_ATSC_VSB_MSK = 3;

	public const int MOD_DTMB_5MHZ = 1;

	public const int MOD_DTMB_6MHZ = 2;

	public const int MOD_DTMB_7MHZ = 3;

	public const int MOD_DTMB_8MHZ = 4;

	public const int MOD_DTMB_BW_AUTO = 15;

	public const int MOD_DTMB_BW_UNK = 15;

	public const int MOD_DTMB_BW_MSK = 15;

	public const int MOD_DTMB_0_4 = 256;

	public const int MOD_DTMB_0_6 = 512;

	public const int MOD_DTMB_0_8 = 768;

	public const int MOD_DTMB_RATE_AUTO = 3840;

	public const int MOD_DTMB_RATE_UNK = 3840;

	public const int MOD_DTMB_RATE_MSK = 3840;

	public const int MOD_DTMB_QAM4NR = 4096;

	public const int MOD_DTMB_QAM4 = 8192;

	public const int MOD_DTMB_QAM16 = 12288;

	public const int MOD_DTMB_QAM32 = 16384;

	public const int MOD_DTMB_QAM64 = 20480;

	public const int MOD_DTMB_CO_AUTO = 61440;

	public const int MOD_DTMB_CO_UNK = 61440;

	public const int MOD_DTMB_CO_MSK = 61440;

	public const int MOD_DTMB_PN420 = 65536;

	public const int MOD_DTMB_PN595 = 131072;

	public const int MOD_DTMB_PN945 = 196608;

	public const int MOD_DTMB_PN_AUTO = 983040;

	public const int MOD_DTMB_PN_UNK = 983040;

	public const int MOD_DTMB_PN_MSK = 983040;

	public const int MOD_DTMB_IL_1 = 1048576;

	public const int MOD_DTMB_IL_2 = 2097152;

	public const int MOD_DTMB_IL_AUTO = 15728640;

	public const int MOD_DTMB_IL_UNK = 15728640;

	public const int MOD_DTMB_IL_MSK = 15728640;

	public const int MOD_DTMB_NO_PILOTS = 16777216;

	public const int MOD_DTMB_PILOTS = 33554432;

	public const int MOD_DTMB_PIL_AUTO = 251658240;

	public const int MOD_DTMB_PIL_UNK = 251658240;

	public const int MOD_DTMB_PIL_MSK = 251658240;

	public const int MOD_DTMB_NO_FRM_NO = 268435456;

	public const int MOD_DTMB_USE_FRM_NO = 536870912;

	public const int MOD_DTMB_UFRM_AUTO = -268435456;

	public const int MOD_DTMB_UFRM_UNK = -268435456;

	public const int MOD_DTMB_UFRM_MSK = -268435456;

	public const int MOD_1_2 = 0;

	public const int MOD_2_3 = 1;

	public const int MOD_3_4 = 2;

	public const int MOD_4_5 = 3;

	public const int MOD_5_6 = 4;

	public const int MOD_6_7 = 5;

	public const int MOD_7_8 = 6;

	public const int MOD_1_4 = 7;

	public const int MOD_1_3 = 8;

	public const int MOD_2_5 = 9;

	public const int MOD_3_5 = 10;

	public const int MOD_8_9 = 11;

	public const int MOD_9_10 = 12;

	public const int MOD_CR_AUTO = 15;

	public const int MOD_CR_UNK = 15;

	public const int MOD_1_5 = 16;

	public const int MOD_2_9 = 17;

	public const int MOD_11_45 = 18;

	public const int MOD_4_15 = 19;

	public const int MOD_13_45 = 20;

	public const int MOD_14_45 = 21;

	public const int MOD_9_20 = 22;

	public const int MOD_7_15 = 23;

	public const int MOD_8_15 = 24;

	public const int MOD_11_20 = 25;

	public const int MOD_5_9 = 26;

	public const int MOD_26_45 = 27;

	public const int MOD_28_45 = 28;

	public const int MOD_23_36 = 29;

	public const int MOD_29_45 = 30;

	public const int MOD_31_45 = 31;

	public const int MOD_25_36 = 32;

	public const int MOD_32_45 = 33;

	public const int MOD_13_18 = 34;

	public const int MOD_11_15 = 35;

	public const int MOD_7_9 = 36;

	public const int MOD_77_90 = 37;

	public const int MOD_S_S2_SPECNONINV = 0;

	public const int MOD_S_S2_SPECINV = 16;

	public const int MOD_S_S2_SPECINV_AUTO = 48;

	public const int MOD_S_S2_SPECINV_UNK = 48;

	public const int MOD_S_S2_SPECINV_MSK = 48;

	public const int MOD_S2_NOPILOTS = 0;

	public const int MOD_S2_PILOTS = 1;

	public const int MOD_S2_PILOTS_AUTO = 3;

	public const int MOD_S2_PILOTS_UNK = 3;

	public const int MOD_S2_PILOTS_MSK = 3;

	public const int MOD_S2_SHORTFRM = 8;

	public const int MOD_S2_MEDIUMFRM = 4;

	public const int MOD_S2_LONGFRM = 0;

	public const int MOD_S2_FRM_AUTO = 12;

	public const int MOD_S2_FRM_UNK = 12;

	public const int MOD_S2_FRM_MSK = 12;

	public const int MOD_S2_CONST_AUTO = 0;

	public const int MOD_S2_CONST_E_1 = 64;

	public const int MOD_S2_CONST_R_1 = 128;

	public const int MOD_S2_CONST_MSK = 192;

	public const int MOD_DVBT_5MHZ = 1;

	public const int MOD_DVBT_6MHZ = 2;

	public const int MOD_DVBT_7MHZ = 3;

	public const int MOD_DVBT_8MHZ = 4;

	public const int MOD_DVBT_BW_UNK = 15;

	public const int MOD_DVBT_BW_MSK = 15;

	public const int MOD_DVBT_QPSK = 16;

	public const int MOD_DVBT_QAM16 = 32;

	public const int MOD_DVBT_QAM64 = 48;

	public const int MOD_DVBT_CO_AUTO = 240;

	public const int MOD_DVBT_CO_UNK = 240;

	public const int MOD_DVBT_CO_MSK = 240;

	public const int MOD_DVBT_G_1_32 = 256;

	public const int MOD_DVBT_G_1_16 = 512;

	public const int MOD_DVBT_G_1_8 = 768;

	public const int MOD_DVBT_G_1_4 = 1024;

	public const int MOD_DVBT_GU_AUTO = 3840;

	public const int MOD_DVBT_GU_UNK = 3840;

	public const int MOD_DVBT_GU_MSK = 3840;

	public const int MOD_DVBT_HARCHY_NONE = 0;

	public const int MOD_DVBT_HARCHY_A1 = 16777216;

	public const int MOD_DVBT_HARCHY_A2 = 33554432;

	public const int MOD_DVBT_HARCHY_A4 = 50331648;

	public const int MOD_DVBT_HARCHY_MSK = 251658240;

	public const int MOD_DVBT_INDEPTH = 4096;

	public const int MOD_DVBT_NATIVE = 8192;

	public const int MOD_DVBT_IL_AUTO = 61440;

	public const int MOD_DVBT_IL_UNK = 61440;

	public const int MOD_DVBT_IL_MSK = 61440;

	public const int MOD_DVBT_2K = 65536;

	public const int MOD_DVBT_4K = 131072;

	public const int MOD_DVBT_8K = 196608;

	public const int MOD_DVBT_MD_AUTO = 983040;

	public const int MOD_DVBT_MD_UNK = 983040;

	public const int MOD_DVBT_MD_MSK = 983040;

	public const int MOD_DVBT_S48_OFF = 0;

	public const int MOD_DVBT_S48 = 1048576;

	public const int MOD_DVBT_S48_MSK = 1048576;

	public const int MOD_DVBT_S49_OFF = 0;

	public const int MOD_DVBT_S49 = 2097152;

	public const int MOD_DVBT_S49_MSK = 2097152;

	public const int MOD_DVBT_ENA4849 = 0;

	public const int MOD_DVBT_DIS4849 = 4194304;

	public const int MOD_DVBT_4849_MSK = 4194304;

	public const int MOD_INTERPOL_OFDM = 1;

	public const int MOD_INTERPOL_QAM = 2;

	public const int MOD_INTERPOL_RAW = 0;

	public const int MOD_IQPCK_AUTO = 0;

	public const int MOD_IQPCK_NONE = 1;

	public const int MOD_IQPCK_PCKD = 2;

	public const int MOD_IQPCK_12B = 3;

	public const int MOD_IQPCK_10B = 4;

	public const int MOD_IQPCK_UNK = 255;

	public const int MOD_IQPCK_MSK = 255;

	public const int MOD_ROLLOFF_AUTO = 0;

	public const int MOD_ROLLOFF_NONE = 256;

	public const int MOD_ROLLOFF_3 = 512;

	public const int MOD_ROLLOFF_5 = 768;

	public const int MOD_ROLLOFF_10 = 1024;

	public const int MOD_ROLLOFF_15 = 1280;

	public const int MOD_ROLLOFF_20 = 1536;

	public const int MOD_ROLLOFF_25 = 1792;

	public const int MOD_ROLLOFF_35 = 2048;

	public const int MOD_LPF_0_614 = 2304;

	public const int MOD_LPF_0_686 = 2560;

	public const int MOD_LPF_0_754 = 2816;

	public const int MOD_LPF_0_833 = 3072;

	public const int MOD_LPF_0_850 = 3328;

	public const int MOD_ROLLOFF_UNK = 65280;

	public const int MOD_ROLLOFF_MSK = 65280;

	public const int MOD_T2MI_PID1_MSK = 8191;

	public const int MOD_T2MI_PID1_SHFT = 0;

	public const int MOD_T2MI_PID2_MSK = 536805376;

	public const int MOD_T2MI_PID2_SHFT = 16;

	public const int MOD_T2MI_MULT_MSK = 536870912;

	public const int MOD_J83_MSK = 15;

	public const int MOD_J83_UNK = 15;

	public const int MOD_J83_AUTO = 15;

	public const int MOD_J83_A = 2;

	public const int MOD_J83_B = 3;

	public const int MOD_J83_C = 1;

	public const int MOD_QAMB_I128_J1D = 1;

	public const int MOD_QAMB_I64_J2 = 3;

	public const int MOD_QAMB_I32_J4 = 5;

	public const int MOD_QAMB_I16_J8 = 7;

	public const int MOD_QAMB_I8_J16 = 9;

	public const int MOD_QAMB_I128_J1 = 0;

	public const int MOD_QAMB_I128_J2 = 2;

	public const int MOD_QAMB_I128_J3 = 4;

	public const int MOD_QAMB_I128_J4 = 6;

	public const int MOD_QAMB_I128_J5 = 8;

	public const int MOD_QAMB_I128_J6 = 10;

	public const int MOD_QAMB_I128_J7 = 12;

	public const int MOD_QAMB_I128_J8 = 14;

	public const int MOD_QAMB_IL_UNK = 15;

	public const int MOD_QAMB_IL_AUTO = 15;

	public const int MOD_QAMB_IL_MSK = 15;

	public const int TUNMOD_QAM = 1;

	public const int TUNMOD_ATSC = 2;

	public const int TUNMOD_ISDBT = 3;

	public const int TUNMOD_DVBT = 4;

	public const int TUNMOD_DMBT = 5;

	public const int TUN31_LPFCUTOFF_AUTO = -1;

	public const int TUN31_LPFCUTOFF_1_5MHZ = 0;

	public const int TUN31_LPFCUTOFF_6MHZ = 1;

	public const int TUN31_LPFCUTOFF_7MHZ = 2;

	public const int TUN31_LPFCUTOFF_8MHZ = 3;

	public const int TUN31_LPFCUTOFF_9MHZ = 4;

	public const int TUN31_LPFOFFSET_AUTO = -1;

	public const int TUN31_LPFOFFSET_0 = 0;

	public const int TUN31_LPFOFFSET_4 = 1;

	public const int TUN31_LPFOFFSET_8 = 2;

	public const int TUN31_LPFOFFSET_12 = 3;

	public const int TUN31_HPF_AUTO = -1;

	public const int TUN31_HPF_DIS = 0;

	public const int TUN31_HPF_0_4MHZ = 1;

	public const int TUN31_HPF_0_85MHZ = 2;

	public const int TUN31_HPF_1MHZ = 3;

	public const int TUN31_HPF_1_5MHZ = 4;

	public const int TUN31_NOTCH_AUTO = -1;

	public const int TUN31_NOTCH_DIS = 0;

	public const int TUN31_NOTCH_ENA = 1;

	public const int TUNING_NORMAL = 0;

	public const int TUNING_INDEPENDENT = 1;

	public const int TUNERID_ALL = -1;

	public const int TUNERID_MAIN = 0;

	public const int TUNERID_MEASUREMENT = 1;

	public const int SDI_TOC_ENTRY_UNKNOWN = 0;

	public const int SDI_TOC_ENTRY_ACTVID = 1;

	public const int SDI_TOC_ENTRY_HANC = 2;

	public const int SDI_TOC_ENTRY_VANC = 3;

	public const int SDI_ANC_TYPE1 = 1;

	public const int SDI_ANC_TYPE2 = 2;

	public const int SDI_PARSE_ACTVID = 1;

	public const int SDI_PARSE_HBLANK = 2;

	public const int SDI_PARSE_VBLANK = 4;

	public const int SDI_PARSE_BLANK = 6;

	public const int SDI_PARSE_ALL = 7;

	public const int SDI_FIELD1 = 1;

	public const int SDI_FIELD2 = 2;

	public const int SDI_AUDIO_GROUP1 = 767;

	public const int SDI_AUDIO_GROUP2 = 509;

	public const int SDI_AUDIO_GROUP3 = 507;

	public const int SDI_AUDIO_GROUP4 = 761;

	public const int SDI_AUDIO_CHAN1 = 1;

	public const int SDI_AUDIO_CHAN2 = 2;

	public const int SDI_AUDIO_CHAN3 = 4;

	public const int SDI_AUDIO_CHAN4 = 8;

	public const int SDI_AUDIO_CH_PAIR1 = 3;

	public const int SDI_AUDIO_CH_PAIR2 = 12;

	public const int SDI_AUDIO_CH_MASK = 15;

	public const int SDI_FULL = 1;

	public const int SDI_ACTVID = 2;

	public const int SDI_HUFFMAN = 4;

	public const int SDI_625 = 8;

	public const int SDI_525 = 16;

	public const int SDI_8B = 32;

	public const int SDI_10B = 64;

	public const int SDI_16B = 128;

	public const int SDI_10B_NBO = 256;

	public const int SDI_BIT_MASK = 480;

	public const int VIDSTD_UNKNOWN = -1;

	public const int VIDSTD_525I59_94 = 88;

	public const int VIDSTD_625I50 = 89;

	public const int VIDSTD_720P23_98 = 80;

	public const int VIDSTD_720P24 = 81;

	public const int VIDSTD_720P25 = 82;

	public const int VIDSTD_720P29_97 = 83;

	public const int VIDSTD_720P30 = 84;

	public const int VIDSTD_720P50 = 85;

	public const int VIDSTD_720P59_94 = 86;

	public const int VIDSTD_720P60 = 87;

	public const int VIDSTD_1080P23_98 = 70;

	public const int VIDSTD_1080P24 = 71;

	public const int VIDSTD_1080P25 = 72;

	public const int VIDSTD_1080P29_97 = 73;

	public const int VIDSTD_1080P30 = 74;

	public const int VIDSTD_1080PSF23_98 = 75;

	public const int VIDSTD_1080PSF24 = 76;

	public const int VIDSTD_1080PSF25 = 77;

	public const int VIDSTD_1080PSF29_97 = 78;

	public const int VIDSTD_1080PSF30 = 79;

	public const int VIDSTD_1080I50 = 67;

	public const int VIDSTD_1080I59_94 = 68;

	public const int VIDSTD_1080I60 = 69;

	public const int VIDSTD_1080P50 = 56;

	public const int VIDSTD_1080P59_94 = 58;

	public const int VIDSTD_1080P60 = 60;

	public const int VIDSTD_2160P50 = 50;

	public const int VIDSTD_2160P59_94 = 52;

	public const int VIDSTD_2160P60 = 54;

	public const int VIDSTD_2160P23_98 = 62;

	public const int VIDSTD_2160P24 = 63;

	public const int VIDSTD_2160P25 = 64;

	public const int VIDSTD_2160P29_97 = 65;

	public const int VIDSTD_2160P30 = 66;

	public const int VIDLNK_4K_SMPTE425 = 0;

	public const int VIDLNK_4K_SMPTE425B = 1;

	public const int SDI_AUDIO_SMPTE272A = 1;

	public const int SDI_AUDIO_PCM16 = 0;

	public const int SDI_AUDIO_PCM24 = 1;

	public const int SDI_AUDIO_PCM32 = 2;

	public const int SDI_HANC = 2;

	public const int SDI_VANC = 4;

	public const int SDI_ANC_MASK = 6;

	public const int SDI_CHROM = 1;

	public const int SDI_LUM = 2;

	public const int SDI_STREAM_MASK = 15;

	public const int ANC_MARK = 1;

	public const int ANC_DELETE = 2;

	public const int SCALING_OFF = 1;

	public const int SCALING_1_4 = 2;

	public const int SCALING_1_16 = 3;

	public const int SYMFLT_ALL = 0;

	public const int SYMFLT_LUM = 1;

	public const int SYMFLT_CHROM = 2;

	public const int SYMFLT_SWAP = 3;

	public const int SYMFLT_RGB = 4;

	public const int ANCFLT_OFF = 0;

	public const int ANCFLT_HANC_ALL = 1;

	public const int ANCFLT_HANC_MIN = 2;

	public const int ANCFLT_VANC_ALL = 3;

	public const int ANCFLT_VANC_MIN = 4;

	public const int ATSC3_NUM_PLP_MAX = 64;

	public const int ATSC3_NUM_RF_MAX = 7;

	public const int ATSC3_CONSTEL_L1_BASIC = -1;

	public const int ATSC3_CONSTEL_L1_DETAIL = -2;

	public const int ATSC3_PLP_ID_AUTO = -2;

	public const int ATSC3_6MHZ = 0;

	public const int ATSC3_7MHZ = 1;

	public const int ATSC3_8MHZ = 2;

	public const int ATSC3_GT8MHZ = 3;

	public const int ATSC3_GI_1_192 = 1;

	public const int ATSC3_GI_2_384 = 2;

	public const int ATSC3_GI_3_512 = 3;

	public const int ATSC3_GI_4_768 = 4;

	public const int ATSC3_GI_5_1024 = 5;

	public const int ATSC3_GI_6_1536 = 6;

	public const int ATSC3_GI_7_2048 = 7;

	public const int ATSC3_GI_8_2432 = 8;

	public const int ATSC3_GI_9_3072 = 9;

	public const int ATSC3_GI_10_3648 = 10;

	public const int ATSC3_GI_11_4096 = 11;

	public const int ATSC3_GI_12_4864 = 12;

	public const int ATSC3_PP_3_2 = 0;

	public const int ATSC3_PP_3_4 = 1;

	public const int ATSC3_PP_4_2 = 2;

	public const int ATSC3_PP_4_4 = 3;

	public const int ATSC3_PP_6_2 = 4;

	public const int ATSC3_PP_6_4 = 5;

	public const int ATSC3_PP_8_2 = 6;

	public const int ATSC3_PP_8_4 = 7;

	public const int ATSC3_PP_12_2 = 8;

	public const int ATSC3_PP_12_4 = 9;

	public const int ATSC3_PP_16_2 = 10;

	public const int ATSC3_PP_16_4 = 11;

	public const int ATSC3_PP_24_2 = 12;

	public const int ATSC3_PP_24_4 = 13;

	public const int ATSC3_PP_32_2 = 14;

	public const int ATSC3_PP_32_4 = 15;

	public const int ATSC3_PP_DX_3 = 0;

	public const int ATSC3_PP_DX_4 = 1;

	public const int ATSC3_PP_DX_6 = 2;

	public const int ATSC3_PP_DX_8 = 3;

	public const int ATSC3_PP_DX_12 = 4;

	public const int ATSC3_PP_DX_16 = 5;

	public const int ATSC3_PP_DX_24 = 6;

	public const int ATSC3_PP_DX_32 = 7;

	public const int ATSC3_QPSK = 0;

	public const int ATSC3_QAM16 = 1;

	public const int ATSC3_QAM64 = 2;

	public const int ATSC3_QAM256 = 3;

	public const int ATSC3_QAM1024 = 4;

	public const int ATSC3_QAM4096 = 5;

	public const int ATSC3_COD_2_15 = 0;

	public const int ATSC3_COD_3_15 = 1;

	public const int ATSC3_COD_4_15 = 2;

	public const int ATSC3_COD_5_15 = 3;

	public const int ATSC3_COD_6_15 = 4;

	public const int ATSC3_COD_7_15 = 5;

	public const int ATSC3_COD_8_15 = 6;

	public const int ATSC3_COD_9_15 = 7;

	public const int ATSC3_COD_10_15 = 8;

	public const int ATSC3_COD_11_15 = 9;

	public const int ATSC3_COD_12_15 = 10;

	public const int ATSC3_COD_13_15 = 11;

	public const int ATSC3_LDPC_16K = 0;

	public const int ATSC3_LDPC_64K = 1;

	public const int ATSC3_OUTER_BCH = 0;

	public const int ATSC3_OUTER_CRC = 1;

	public const int ATSC3_OUTER_NONE = 2;

	public const int ATSC3_LAYER_CORE = 0;

	public const int ATSC3_LAYER_ENHANCED = 1;

	public const int ATSC3_TIMODE_NONE = 0;

	public const int ATSC3_TIMODE_CTI = 1;

	public const int ATSC3_TIMODE_HTI = 2;

	public const int ATSC3_PLPTYPE_NONDISP = 0;

	public const int ATSC3_PLPTYPE_DISP = 1;

	public const int ATSC3_CTIDEPTH_512 = 0;

	public const int ATSC3_CTIDEPTH_724 = 1;

	public const int ATSC3_CTIDEPTH_887 = 2;

	public const int ATSC3_CTIDEPTH_1024 = 3;

	public const int ATSC3_MISO_NONE = 0;

	public const int ATSC3_MISO_64 = 1;

	public const int ATSC3_MISO_256 = 2;

	public const int ATSC3_FFT_8K = 0;

	public const int ATSC3_FFT_16K = 1;

	public const int ATSC3_FFT_32K = 2;

	public const int ATSC3_PAPR_NONE = 0;

	public const int ATSC3_PAPR_TR = 1;

	public const int ATSC3_PAPR_ACE = 2;

	public const int ATSC3_PAPR_ACE_TR = 3;

	public const int ATSC3_TIME_NONE = 0;

	public const int ATSC3_TIME_MS = 1;

	public const int ATSC3_TIME_US = 2;

	public const int ATSC3_TIME_NS = 3;

	public const int ATSC3_ALIGN_TIME = 0;

	public const int ATSC3_ALIGN_SYMBOL = 1;

	public const int TP_FORMAT_HEX = 0;

	public const int TP_FORMAT_BIT = 1;

	public const int TP_FORMAT_CFLOAT32 = 2;

	public const int TP_FORMAT_INT64 = 3;

	public const int DVBC2_NUM_DSLICE_MAX = 255;

	public const int DVBC2_NUM_PLP_MAX = 255;

	public const int DVBC2_NUM_NOTCH_MAX = 16;

	public const int DVBC2_PLP_ID_NONE = -1;

	public const int DVBC2_PLP_ID_AUTO = -2;

	public const int DVBC2_DSLICE_ID_AUTO = -2;

	public const int DVBC2_6MHZ = 6;

	public const int DVBC2_8MHZ = 8;

	public const int DVBC2_GI_1_128 = 0;

	public const int DVBC2_GI_1_64 = 1;

	public const int DVBC2_L1TIMODE_NONE = 0;

	public const int DVBC2_L1TIMODE_BEST = 1;

	public const int DVBC2_L1TIMODE_4 = 2;

	public const int DVBC2_L1TIMODE_8 = 3;

	public const int DVBC2_PLP_TYPE_COMMON = 0;

	public const int DVBC2_PLP_TYPE_GROUPED = 1;

	public const int DVBC2_PLP_TYPE_NORMAL = 2;

	public const int DVBC2_LDPC_16K = 0;

	public const int DVBC2_LDPC_64K = 1;

	public const int DVBC2_COD_2_3 = 1;

	public const int DVBC2_COD_3_4 = 2;

	public const int DVBC2_COD_4_5 = 3;

	public const int DVBC2_COD_5_6 = 4;

	public const int DVBC2_COD_8_9 = 5;

	public const int DVBC2_COD_9_10 = 5;

	public const int DVBC2_QAM16 = 1;

	public const int DVBC2_QAM64 = 2;

	public const int DVBC2_QAM256 = 3;

	public const int DVBC2_QAM1024 = 4;

	public const int DVBC2_QAM4096 = 5;

	public const int DVBC2_QAM16384 = 6;

	public const int DVBC2_QAM65536 = 7;

	public const int DVBC2_VERSION_1_2_1 = 0;

	public const int DVBC2_VERSION_1_3_1 = 1;

	public const int DVBC2_ISSY_NONE = 0;

	public const int DVBC2_ISSY_SHORT = 1;

	public const int DVBC2_ISSY_LONG = 2;

	public const int DVBC2_TIDEPTH_NONE = 0;

	public const int DVBC2_TIDEPTH_4 = 1;

	public const int DVBC2_TIDEPTH_8 = 2;

	public const int DVBC2_TIDEPTH_16 = 3;

	public const int DVBC2_DSLICE_TYPE_1 = 0;

	public const int DVBC2_DSLICE_TYPE_2 = 1;

	public const int DVBC2_FECHDR_TYPE_ROBUST = 0;

	public const int DVBC2_FECHDR_TYPE_HEM = 1;

	public const int DVBC2_TP07 = 0;

	public const int DVBC2_TP08 = 1;

	public const int DVBC2_TP10 = 2;

	public const int DVBC2_TP13 = 3;

	public const int DVBC2_TP15 = 4;

	public const int DVBC2_TP18 = 5;

	public const int DVBC2_TP20 = 6;

	public const int DVBC2_TP22 = 7;

	public const int DVBC2_TP26 = 8;

	public const int DVBC2_TP27 = 9;

	public const int DVBC2_TP31 = 10;

	public const int DVBC2_TP32 = 11;

	public const int DVBC2_TP33 = 12;

	public const int DVBC2_TP37 = 13;

	public const int DVBC2_TP40 = 14;

	public const int DVBC2_TP41 = 15;

	public const int DVBC2_TP42 = 16;

	public const int DVBC2_TP01 = 17;

	public const int DVBC2_TP_COUNT = 18;

	public const int DVBC2_PAYLOAD_GFPS = 0;

	public const int DVBC2_PAYLOAD_GCS = 1;

	public const int DVBC2_PAYLOAD_GSE = 2;

	public const int DVBC2_PAYLOAD_TS = 3;

	public const int DVBT2_NUM_PLP_MAX = 255;

	public const int DVBT2_NUM_RF_MAX = 7;

	public const int DVBT2_PLP_ID_NONE = -1;

	public const int DVBT2_PLP_ID_AUTO = -2;

	public const int DVBT2_ISSY_NONE = 0;

	public const int DVBT2_ISSY_SHORT = 1;

	public const int DVBT2_ISSY_LONG = 2;

	public const int DVBT2_1_7MHZ = 0;

	public const int DVBT2_5MHZ = 1;

	public const int DVBT2_6MHZ = 2;

	public const int DVBT2_7MHZ = 3;

	public const int DVBT2_8MHZ = 4;

	public const int DVBT2_10MHZ = 5;

	public const int DVBT2_FFT_1K = 0;

	public const int DVBT2_FFT_2K = 1;

	public const int DVBT2_FFT_4K = 2;

	public const int DVBT2_FFT_8K = 3;

	public const int DVBT2_FFT_16K = 4;

	public const int DVBT2_FFT_32K = 5;

	public const int DVBT2_MISO_OFF = 0;

	public const int DVBT2_MISO_TX1 = 1;

	public const int DVBT2_MISO_TX2 = 2;

	public const int DVBT2_MISO_TX1TX2 = 3;

	public const int DVBT2_MISO_SUM = 3;

	public const int DVBT2_MISO_BOTH = 4;

	public const int DVBT2_GI_1_128 = 0;

	public const int DVBT2_GI_1_32 = 1;

	public const int DVBT2_GI_1_16 = 2;

	public const int DVBT2_GI_19_256 = 3;

	public const int DVBT2_GI_1_8 = 4;

	public const int DVBT2_GI_19_128 = 5;

	public const int DVBT2_GI_1_4 = 6;

	public const int DVBT2_PAPR_NONE = 0;

	public const int DVBT2_PAPR_ACE = 1;

	public const int DVBT2_PAPR_TR = 2;

	public const int DVBT2_PAPR_ACE_TR = 3;

	public const bool DVBT2_BWTEXT_OFF = false;

	public const bool DVBT2_BWTEXT_ON = true;

	public const int DVBT2_PP_1 = 1;

	public const int DVBT2_PP_2 = 2;

	public const int DVBT2_PP_3 = 3;

	public const int DVBT2_PP_4 = 4;

	public const int DVBT2_PP_5 = 5;

	public const int DVBT2_PP_6 = 6;

	public const int DVBT2_PP_7 = 7;

	public const int DVBT2_PP_8 = 8;

	public const int DVBT2_COD_1_2 = 0;

	public const int DVBT2_COD_3_5 = 1;

	public const int DVBT2_COD_2_3 = 2;

	public const int DVBT2_COD_3_4 = 3;

	public const int DVBT2_COD_4_5 = 4;

	public const int DVBT2_COD_5_6 = 5;

	public const int DVBT2_COD_1_3 = 6;

	public const int DVBT2_COD_2_5 = 7;

	public const int DVBT2_FEF_ZERO = 0;

	public const int DVBT2_FEF_1K_OFDM = 1;

	public const int DVBT2_FEF_1K_OFDM_384 = 2;

	public const int DVBT2_BPSK = 0;

	public const int DVBT2_QPSK = 1;

	public const int DVBT2_QAM16 = 2;

	public const int DVBT2_QAM64 = 3;

	public const int DVBT2_QAM256 = 4;

	public const int DVBT2_PLP_TYPE_COMM = 0;

	public const int DVBT2_PLP_TYPE_1 = 1;

	public const int DVBT2_PLP_TYPE_2 = 2;

	public const int DVBT2_LDPC_16K = 0;

	public const int DVBT2_LDPC_64K = 1;

	public const int DVBT2_IL_ONETOONE = 0;

	public const int DVBT2_IL_MULTI = 1;

	public const int DVBT2MI_TIMESTAMP_NULL = 0;

	public const int DVBT2MI_TIMESTAMP_REL = 1;

	public const int DVBT2MI_TIMESTAMP_ABS = 2;

	public const int DVBT2_VERSION_1_1_1 = 0;

	public const int DVBT2_VERSION_1_2_1 = 1;

	public const int DVBT2_VERSION_1_3_1 = 2;

	public const int DVBT2_PROFILE_BASE = 0;

	public const int DVBT2_PROFILE_LITE = 1;

	public const int DVBT2_BIAS_BAL_OFF = 0;

	public const int DVBT2_BIAS_BAL_ON = 1;

	public const int DVBT2_GSE_LABEL_6BYTE = 0;

	public const int DVBT2_GSE_LABEL_3BYTE = 1;

	public const int DVBT2_GSE_LABEL_NONE = 2;

	public const int TXSIG_FEF_LEN_MIN = 162212;

	public const int DVBT2_TP00 = 0;

	public const int DVBT2_TP01 = 1;

	public const int DVBT2_TP03 = 2;

	public const int DVBT2_TP04 = 3;

	public const int DVBT2_TP06 = 4;

	public const int DVBT2_TP08 = 5;

	public const int DVBT2_TP09 = 6;

	public const int DVBT2_TP11 = 7;

	public const int DVBT2_TP12 = 8;

	public const int DVBT2_TP15 = 9;

	public const int DVBT2_TP16 = 10;

	public const int DVBT2_TP19 = 11;

	public const int DVBT2_TP20 = 12;

	public const int DVBT2_TP21 = 13;

	public const int DVBT2_TP22 = 14;

	public const int DVBT2_TP23 = 15;

	public const int DVBT2_TP24 = 16;

	public const int DVBT2_TP25 = 17;

	public const int DVBT2_TP26 = 18;

	public const int DVBT2_TP27 = 19;

	public const int DVBT2_TP28 = 20;

	public const int DVBT2_TP29 = 21;

	public const int DVBT2_TP30 = 22;

	public const int DVBT2_TP32 = 23;

	public const int DVBT2_TP33 = 24;

	public const int DVBT2_TP34 = 25;

	public const int DVBT2_TP50 = 26;

	public const int DVBT2_TP51 = 27;

	public const int DVBT2_TP53 = 28;

	public const int DVBT2_TP_COUNT = 29;

	public const int DVBT2_COMPA_ALL = 0;

	public const int DVBT2_COMPA_ESSENTIAL = 1;

	public const int DVBT2_PAYLOAD_GFPS = 0;

	public const int DVBT2_PAYLOAD_GCS = 1;

	public const int DVBT2_PAYLOAD_GSE = 2;

	public const int DVBT2_PAYLOAD_TS = 3;

	public const int DVBT2_TYPE_TS = 0;

	public const int DVBT2_TYPE_GS = 1;

	public const int DVBT2_TYPE_TS_GS = 2;

	unsafe static DTAPI()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps);
		CAP_EMPTY = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps2);
		CAP_C2X = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps2, 0));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps3);
		CAP_DP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps3, 1));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps4);
		CAP_DTTV = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps4, 2));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps5);
		CAP_E = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps5, 3));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps6);
		CAP_J = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps6, 4));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps7);
		CAP_J2K = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps7, 5));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps8);
		CAP_MR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps8, 6));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps9);
		CAP_MS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps9, 7));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps10);
		CAP_MX = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps10, 8));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps11);
		CAP_RC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps11, 9));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps12);
		CAP_RX = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps12, 10));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps13);
		CAP_SL = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps13, 11));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps14);
		CAP_SP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps14, 12));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps15);
		CAP_SX = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps15, 14));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps16);
		CAP_SXNIC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps16, 15));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps17);
		CAP_SY = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps17, 16));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps18);
		CAP_XP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps18, 20));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps19);
		CAP_VR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps19, 18));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps20);
		CAP_VRDGL = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps20, 19));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps21);
		CAP_T2X = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps21, 17));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps22);
		CAP_BW = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps22, 28));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps23);
		CAP_FAILSAFE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps23, 30));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps24);
		CAP_FRACMODE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps24, 31));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps25);
		CAP_GENLOCKED = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps25, 32));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps26);
		CAP_GENREF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps26, 33));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps27);
		CAP_SWS2APSK = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps27, 34));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps28);
		CAP_ANTPWR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps28, 35));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps29);
		CAP_LNB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps29, 36));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps30);
		CAP_RX_ADV = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps30, 37));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps31);
		CAP_LBAND = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps31, 44));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps32);
		CAP_VHF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps32, 45));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps33);
		CAP_UHF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps33, 46));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps34);
		CAP_DISABLED = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps34, 51));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps35);
		CAP_INPUT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps35, 52));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps36);
		CAP_OUTPUT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps36, 56));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps37);
		CAP_SHAREDANT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps37, 57));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps38);
		CAP_DBLBUF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps38, 58));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps39);
		CAP_LOOPS2L3 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps39, 59));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps40);
		CAP_LOOPS2TS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps40, 60));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps41);
		CAP_LOOPTHR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps41, 61));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps42);
		CAP_ASIPOL = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps42, 62));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps43);
		CAP_HUFFMAN = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps43, 65));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps44);
		CAP_IPPAIR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps44, 66));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps45);
		CAP_L3MODE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps45, 67));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps46);
		CAP_MATRIX = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps46, 68));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps47);
		CAP_MATRIX2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps47, 69));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps48);
		CAP_RAWASI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps48, 72));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps49);
		CAP_SDI10BNBO = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps49, 73));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps50);
		CAP_SDITIME = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps50, 74));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps51);
		CAP_TIMESTAMP64 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps51, 78));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps52);
		CAP_TRPMODE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps52, 79));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps53);
		CAP_TS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps53, 80));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps54);
		CAP_TXONTIME = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps54, 81));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps55);
		CAP_3GSDI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps55, 84));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps56);
		CAP_ASI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps56, 86));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps57);
		CAP_DEMOD = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps57, 89));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps58);
		CAP_GPSTIME = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps58, 90));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps59);
		CAP_HDSDI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps59, 92));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps60);
		CAP_IFADC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps60, 93));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps61);
		CAP_IP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps61, 94));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps62);
		CAP_MOD = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps62, 95));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps63);
		CAP_PHASENOISE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps63, 96));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps64);
		CAP_RS422 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps64, 97));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps65);
		CAP_SDI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps65, 99));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps66);
		CAP_SPI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps66, 100));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps67);
		CAP_SPISDI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps67, 101));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps68);
		CAP_1080P50 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps68, 108));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps69);
		CAP_1080P59_94 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps69, 110));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps70);
		CAP_1080P60 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps70, 112));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps71);
		CAP_1080I50 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps71, 119));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps72);
		CAP_1080I59_94 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps72, 120));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps73);
		CAP_1080I60 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps73, 121));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps74);
		CAP_1080P23_98 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps74, 122));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps75);
		CAP_1080P24 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps75, 123));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps76);
		CAP_1080P25 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps76, 124));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps77);
		CAP_1080P29_97 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps77, 125));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps78);
		CAP_1080P30 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps78, 126));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps79);
		CAP_720P23_98 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps79, 132));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps80);
		CAP_720P24 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps80, 133));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps81);
		CAP_720P25 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps81, 134));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps82);
		CAP_720P29_97 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps82, 135));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps83);
		CAP_720P30 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps83, 136));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps84);
		CAP_720P50 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps84, 137));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps85);
		CAP_720P59_94 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps85, 138));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps86);
		CAP_720P60 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps86, 139));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps87);
		CAP_525I59_94 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps87, 140));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps88);
		CAP_625I50 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps88, 141));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps89);
		CAP_SPI525I59_94 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps89, 142));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps90);
		CAP_SPI625I50 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps90, 143));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps91);
		CAP_TX_ATSC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps91, 148));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps92);
		CAP_TX_ATSC3 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps92, 149));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps93);
		CAP_TX_CMMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps93, 150));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps94);
		CAP_TX_DAB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps94, 151));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps95);
		CAP_TX_DTMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps95, 153));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps96);
		CAP_TX_DVBC2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps96, 154));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps97);
		CAP_TX_DVBS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps97, 155));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps98);
		CAP_TX_DVBS2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps98, 156));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps99);
		CAP_TX_DVBS2X = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps99, 157));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps100);
		CAP_TX_DVBT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps100, 158));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps101);
		CAP_TX_DVBT2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps101, 159));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps102);
		CAP_TX_GOLD = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps102, 160));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps103);
		CAP_TX_IQ = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps103, 162));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps104);
		CAP_TX_ISDBS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps104, 163));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps105);
		CAP_TX_ISDBT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps105, 165));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps106);
		CAP_TX_MH = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps106, 167));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps107);
		CAP_TX_QAMA = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps107, 168));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps108);
		CAP_TX_QAMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps108, 169));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps109);
		CAP_TX_QAMC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps109, 170));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps110);
		CAP_TX_SWMC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps110, 171));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps111);
		CAP_TX_T2MI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps111, 172));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps112);
		CAP_TX_T2SPLP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps112, 173));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps113);
		CAP_ADJLVL = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps113, 174));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps114);
		CAP_CM = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps114, 175));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps115);
		CAP_CW = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps115, 176));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps116);
		CAP_DIGIQ = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps116, 177));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps117);
		CAP_DVBCID = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps117, 178));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps118);
		CAP_IF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps118, 179));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps119);
		CAP_MUTE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps119, 180));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps120);
		CAP_ROLLOFF = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps120, 181));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps121);
		CAP_S2APSK = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps121, 182));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps122);
		CAP_SNR = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps122, 183));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps123);
		CAP_TX_16MHZ = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps123, 185));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps124);
		CAP_TX_SFN = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps124, 186));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps125);
		CAP_RFCLKEXT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps125, 187));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps126);
		CAP_RFCLKINT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps126, 188));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps127);
		CAP_RX_ATSC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps127, 189));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps128);
		CAP_RX_ATSC3 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps128, 190));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps129);
		CAP_RX_CMMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps129, 192));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps130);
		CAP_RX_DAB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps130, 193));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps131);
		CAP_RX_DTMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps131, 194));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps132);
		CAP_RX_DVBC2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps132, 195));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps133);
		CAP_RX_DVBS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps133, 197));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps134);
		CAP_RX_DVBS2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps134, 198));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps135);
		CAP_RX_DVBT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps135, 200));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps136);
		CAP_RX_DVBT2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps136, 201));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps137);
		CAP_RX_GOLD = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps137, 202));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps138);
		CAP_RX_IQ = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps138, 203));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps139);
		CAP_RX_ISDBS = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps139, 204));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps140);
		CAP_RX_ISDBT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps140, 205));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps141);
		CAP_RX_MH = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps141, 207));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps142);
		CAP_RX_QAMA = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps142, 208));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps143);
		CAP_RX_QAMB = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps143, 209));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps144);
		CAP_RX_QAMC = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps144, 210));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps145);
		CAP_RX_T2MI = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps145, 211));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps146);
		CAP_SPICLKEXT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps146, 212));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps147);
		CAP_SPICLKINT = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps147, 213));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps148);
		CAP_SPIFIXEDCLK = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps148, 214));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps149);
		CAP_SPIDVBMODE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps149, 215));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps150);
		CAP_SPISER8B = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps150, 216));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps151);
		CAP_SPISER10B = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps151, 217));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps152);
		CAP_SPILVDS1 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps152, 218));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps153);
		CAP_SPILVDS2 = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps153, 219));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps154);
		CAP_SPILVTTL = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps154, 220));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps155);
		CAP_EXTTSRATE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps155, 221));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps156);
		CAP_EXTRATIO = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps156, 222));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps157);
		CAP_INTTSRATE = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps157, 223));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCaps dtCaps158);
		CAP_LOCK2INP = new DtCaps(global::_003CModule_003E.Dtapi_002EDtCaps_002E_007Bctor_007D(&dtCaps158, 224));
	}
}
