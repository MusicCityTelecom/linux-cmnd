using Dtapi;

namespace DTAPINET;

public struct DtIpStat
{
	public uint m_TotNumIpPackets;

	public uint m_LostIpPacketsBeforeFec;

	public uint m_LostIpPacketsAfterFec;

	public uint m_NumIpPacketsReceived1;

	public uint m_NumIpPacketsReceived2;

	public uint m_NumIpPacketsLost1;

	public uint m_NumIpPacketsLost2;

	public DtIpQosStats m_QosStatsLastSec;

	public DtIpQosStats m_QosStatsLastMin;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIpStat* uTsIpStat)
	{
		m_TotNumIpPackets = *(uint*)uTsIpStat;
		m_LostIpPacketsBeforeFec = ((uint*)uTsIpStat)[1];
		m_LostIpPacketsAfterFec = ((uint*)uTsIpStat)[2];
		m_NumIpPacketsReceived1 = ((uint*)uTsIpStat)[3];
		m_NumIpPacketsReceived2 = ((uint*)uTsIpStat)[4];
		m_NumIpPacketsLost1 = ((uint*)uTsIpStat)[5];
		m_NumIpPacketsLost2 = ((uint*)uTsIpStat)[6];
		m_QosStatsLastSec.m_Per1 = ((double*)uTsIpStat)[4];
		m_QosStatsLastSec.m_Per2 = ((double*)uTsIpStat)[5];
		m_QosStatsLastSec.m_PerAfterFec = ((double*)uTsIpStat)[6];
		m_QosStatsLastSec.m_DelayFactor1 = ((double*)uTsIpStat)[7];
		m_QosStatsLastSec.m_DelayFactor2 = ((double*)uTsIpStat)[8];
		m_QosStatsLastSec.m_MinSkew = ((double*)uTsIpStat)[9];
		m_QosStatsLastSec.m_MaxSkew = ((double*)uTsIpStat)[10];
		m_QosStatsLastSec.m_MinIpat1 = ((double*)uTsIpStat)[11];
		m_QosStatsLastSec.m_MinIpat2 = ((double*)uTsIpStat)[12];
		m_QosStatsLastSec.m_MaxIpat1 = ((double*)uTsIpStat)[13];
		m_QosStatsLastSec.m_MaxIpat2 = ((double*)uTsIpStat)[14];
		m_QosStatsLastMin.m_Per1 = ((double*)uTsIpStat)[15];
		m_QosStatsLastMin.m_Per2 = ((double*)uTsIpStat)[16];
		m_QosStatsLastMin.m_PerAfterFec = ((double*)uTsIpStat)[17];
		m_QosStatsLastMin.m_DelayFactor1 = ((double*)uTsIpStat)[18];
		m_QosStatsLastMin.m_DelayFactor2 = ((double*)uTsIpStat)[19];
		m_QosStatsLastMin.m_MinSkew = ((double*)uTsIpStat)[20];
		m_QosStatsLastMin.m_MaxSkew = ((double*)uTsIpStat)[21];
		m_QosStatsLastMin.m_MinIpat1 = ((double*)uTsIpStat)[22];
		m_QosStatsLastMin.m_MinIpat2 = ((double*)uTsIpStat)[23];
		m_QosStatsLastMin.m_MaxIpat1 = ((double*)uTsIpStat)[24];
		m_QosStatsLastMin.m_MaxIpat2 = ((double*)uTsIpStat)[25];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIpStat* uTsIpStat)
	{
		*(uint*)uTsIpStat = m_TotNumIpPackets;
		((int*)uTsIpStat)[1] = (int)m_LostIpPacketsBeforeFec;
		((int*)uTsIpStat)[2] = (int)m_LostIpPacketsAfterFec;
		((int*)uTsIpStat)[3] = (int)m_NumIpPacketsReceived1;
		((int*)uTsIpStat)[4] = (int)m_NumIpPacketsReceived2;
		((int*)uTsIpStat)[5] = (int)m_NumIpPacketsLost1;
		((int*)uTsIpStat)[6] = (int)m_NumIpPacketsLost2;
		((double*)uTsIpStat)[4] = m_QosStatsLastSec.m_Per1;
		((double*)uTsIpStat)[5] = m_QosStatsLastSec.m_Per2;
		((double*)uTsIpStat)[6] = m_QosStatsLastSec.m_PerAfterFec;
		((double*)uTsIpStat)[7] = m_QosStatsLastSec.m_DelayFactor1;
		((double*)uTsIpStat)[8] = m_QosStatsLastSec.m_DelayFactor2;
		((double*)uTsIpStat)[9] = m_QosStatsLastSec.m_MinSkew;
		((double*)uTsIpStat)[10] = m_QosStatsLastSec.m_MaxSkew;
		((double*)uTsIpStat)[11] = m_QosStatsLastSec.m_MinIpat1;
		((double*)uTsIpStat)[12] = m_QosStatsLastSec.m_MinIpat2;
		((double*)uTsIpStat)[13] = m_QosStatsLastSec.m_MaxIpat1;
		((double*)uTsIpStat)[14] = m_QosStatsLastSec.m_MaxIpat2;
		((double*)uTsIpStat)[15] = m_QosStatsLastMin.m_Per1;
		((double*)uTsIpStat)[16] = m_QosStatsLastMin.m_Per2;
		((double*)uTsIpStat)[17] = m_QosStatsLastMin.m_PerAfterFec;
		((double*)uTsIpStat)[18] = m_QosStatsLastMin.m_DelayFactor1;
		((double*)uTsIpStat)[19] = m_QosStatsLastMin.m_DelayFactor2;
		((double*)uTsIpStat)[20] = m_QosStatsLastMin.m_MinSkew;
		((double*)uTsIpStat)[21] = m_QosStatsLastMin.m_MaxSkew;
		((double*)uTsIpStat)[22] = m_QosStatsLastMin.m_MinIpat1;
		((double*)uTsIpStat)[23] = m_QosStatsLastMin.m_MinIpat2;
		((double*)uTsIpStat)[24] = m_QosStatsLastMin.m_MaxIpat1;
		((double*)uTsIpStat)[25] = m_QosStatsLastMin.m_MaxIpat2;
	}
}
