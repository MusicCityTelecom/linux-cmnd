using Dtapi;

namespace DTAPINET;

public class DtBsProgress
{
	public enum BsEvent
	{
		BS_STEP,
		BS_CANCELLED,
		BS_DONE
	}

	public long m_FreqHz;

	public readonly DtDemodPars m_DemodPars = new DtDemodPars();

	public BsEvent m_ProgressEvent;

	public bool m_ChannelFound;

	public DTAPI_RESULT m_Result;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtBsProgress* Unmgd)
	{
		m_FreqHz = *(long*)Unmgd;
		m_DemodPars.ConvertFromUnmgd((Dtapi.DtDemodPars*)((byte*)Unmgd + 8));
		m_ProgressEvent = ((BsEvent*)Unmgd)[4];
		m_ChannelFound = ((bool*)Unmgd)[20];
		m_Result = ((DTAPI_RESULT*)Unmgd)[6];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtBsProgress* Unmgd)
	{
		*(long*)Unmgd = m_FreqHz;
		m_DemodPars.ConvertToUnmgd((Dtapi.DtDemodPars*)((byte*)Unmgd + 8));
		((int*)Unmgd)[4] = (int)m_ProgressEvent;
		((sbyte*)Unmgd)[20] = (m_ChannelFound ? ((sbyte)1) : ((sbyte)0));
		((int*)Unmgd)[6] = (int)m_Result;
	}
}
