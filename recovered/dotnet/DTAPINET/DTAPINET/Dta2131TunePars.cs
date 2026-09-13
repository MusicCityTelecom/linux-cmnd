using Dtapi;

namespace DTAPINET;

public class Dta2131TunePars : DtTunePars
{
	private int m_TunerStandard;

	private int m_TunerBandwidth;

	private int m_IfFrequency;

	private int m_LpfCutOff;

	private int m_LpfOffset;

	private int m_HiPass;

	private int m_DcNotchIfPpf;

	private int m_IfNotch;

	private int m_IfNotchToRssi;

	internal unsafe override void ConvertFromUnmgd(Dtapi.DtTunePars* uTunePars)
	{
		m_TunerStandard = *(int*)uTunePars;
		m_TunerBandwidth = ((int*)uTunePars)[1];
		m_IfFrequency = ((int*)uTunePars)[2];
		m_LpfCutOff = ((int*)uTunePars)[3];
		m_LpfOffset = ((int*)uTunePars)[4];
		m_HiPass = ((int*)uTunePars)[5];
		m_DcNotchIfPpf = ((int*)uTunePars)[6];
		m_IfNotch = ((int*)uTunePars)[7];
		m_IfNotchToRssi = ((int*)uTunePars)[8];
	}

	internal unsafe override void ConvertToUnmgd(Dtapi.DtTunePars* uTunePars)
	{
		*(int*)uTunePars = m_TunerStandard;
		((int*)uTunePars)[1] = m_TunerBandwidth;
		((int*)uTunePars)[2] = m_IfFrequency;
		((int*)uTunePars)[3] = m_LpfCutOff;
		((int*)uTunePars)[4] = m_LpfOffset;
		((int*)uTunePars)[5] = m_HiPass;
		((int*)uTunePars)[6] = m_DcNotchIfPpf;
		((int*)uTunePars)[7] = m_IfNotch;
		((int*)uTunePars)[8] = m_IfNotchToRssi;
	}
}
