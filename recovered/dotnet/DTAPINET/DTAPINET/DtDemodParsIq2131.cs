using Dtapi;

namespace DTAPINET;

public class DtDemodParsIq2131 : IDemodPars
{
	public int m_IqDemodFreq;

	public DtFilterPars m_LpfFilter;

	public double m_LpfScaleFactor;

	public int m_SampleRate;

	public DtTunePars m_TunePars;

	public DtDemodParsIq2131()
	{
		m_LpfFilter = new DtFilterPars();
		m_TunePars = null;
	}

	internal unsafe override void ConvertFromUnmgd(void* pDemodPars)
	{
		m_IqDemodFreq = *(int*)pDemodPars;
		m_LpfFilter.ConvertFromUnmgd((Dtapi.DtFilterPars*)((byte*)pDemodPars + 4));
		m_LpfScaleFactor = ((double*)pDemodPars)[2];
		m_SampleRate = ((int*)pDemodPars)[6];
		m_TunePars?.ConvertFromUnmgd((Dtapi.DtTunePars*)((byte*)pDemodPars + 28));
	}

	internal unsafe override void ConvertToUnmgd(void* pDemodPars)
	{
		*(int*)pDemodPars = m_IqDemodFreq;
		m_LpfFilter.ConvertToUnmgd((Dtapi.DtFilterPars*)((byte*)pDemodPars + 4));
		((double*)pDemodPars)[2] = m_LpfScaleFactor;
		((int*)pDemodPars)[6] = m_SampleRate;
		m_TunePars?.ConvertToUnmgd((Dtapi.DtTunePars*)((byte*)pDemodPars + 28));
	}
}
