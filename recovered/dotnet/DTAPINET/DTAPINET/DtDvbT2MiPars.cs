using Dtapi;

namespace DTAPINET;

public class DtDvbT2MiPars
{
	public bool m_Enabled;

	public int m_Pid;

	public int m_StreamId;

	public int m_Pid2;

	public int m_StreamId2;

	public int m_PcrPid;

	public int m_PmtPid;

	public int m_TsRate;

	public bool m_SyncWithExtClock;

	public int m_TimeStamping;

	public long m_SecSince2000;

	public int m_Subseconds;

	public int m_T2miUtco;

	public bool m_EncodeFef;

	internal DtDvbT2MiPars()
	{
		m_Enabled = false;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2MiPars* uT2miPars)
	{
		*(bool*)uT2miPars = m_Enabled;
		((int*)uT2miPars)[1] = m_Pid;
		((int*)uT2miPars)[2] = m_StreamId;
		((int*)uT2miPars)[3] = m_Pid2;
		((int*)uT2miPars)[4] = m_StreamId2;
		((int*)uT2miPars)[5] = m_PcrPid;
		((int*)uT2miPars)[6] = m_PmtPid;
		((int*)uT2miPars)[7] = m_TsRate;
		((sbyte*)uT2miPars)[57] = (m_SyncWithExtClock ? ((sbyte)1) : ((sbyte)0));
		((int*)uT2miPars)[8] = m_TimeStamping;
		((long*)uT2miPars)[5] = m_SecSince2000;
		((int*)uT2miPars)[12] = m_Subseconds;
		((int*)uT2miPars)[13] = m_T2miUtco;
		((sbyte*)uT2miPars)[56] = (m_EncodeFef ? ((sbyte)1) : ((sbyte)0));
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2MiPars* uT2miPars)
	{
		m_Enabled = *(bool*)uT2miPars;
		m_Pid = ((int*)uT2miPars)[1];
		m_StreamId = ((int*)uT2miPars)[2];
		m_Pid2 = ((int*)uT2miPars)[3];
		m_StreamId2 = ((int*)uT2miPars)[4];
		m_PcrPid = ((int*)uT2miPars)[5];
		m_PmtPid = ((int*)uT2miPars)[6];
		m_TsRate = ((int*)uT2miPars)[7];
		m_SyncWithExtClock = ((bool*)uT2miPars)[57];
		m_TimeStamping = ((int*)uT2miPars)[8];
		m_SecSince2000 = ((long*)uT2miPars)[5];
		m_Subseconds = ((int*)uT2miPars)[12];
		m_T2miUtco = ((int*)uT2miPars)[13];
		m_EncodeFef = ((bool*)uT2miPars)[56];
	}
}
