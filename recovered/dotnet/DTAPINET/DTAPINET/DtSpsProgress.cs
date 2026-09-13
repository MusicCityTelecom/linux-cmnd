using Dtapi;

namespace DTAPINET;

public class DtSpsProgress
{
	public enum SpsEvent
	{
		SPS_STEP,
		SPS_CANCELLED,
		SPS_DONE
	}

	public DtRfLevel m_DtRfLevel;

	public SpsEvent m_ProgressEvent;

	public DTAPI_RESULT m_Result;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtSpsProgress* Unmgd)
	{
		ref DtRfLevel dtRfLevel = ref m_DtRfLevel;
		dtRfLevel.m_FreqHz = *(long*)Unmgd;
		dtRfLevel.m_RfLevel = ((int*)Unmgd)[2];
		m_ProgressEvent = ((SpsEvent*)Unmgd)[4];
		m_Result = ((DTAPI_RESULT*)Unmgd)[5];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtSpsProgress* Unmgd)
	{
		ref DtRfLevel dtRfLevel = ref m_DtRfLevel;
		*(long*)Unmgd = dtRfLevel.m_FreqHz;
		((int*)Unmgd)[2] = dtRfLevel.m_RfLevel;
		((int*)Unmgd)[4] = (int)m_ProgressEvent;
		((int*)Unmgd)[5] = (int)m_Result;
	}
}
