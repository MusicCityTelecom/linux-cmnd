using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDvbT2DemodAuxPars
{
	public int m_AuxStreamType;

	public int m_AuxPrivateConf;

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbT2DemodAuxPars* uAuxPars)
	{
		*(int*)uAuxPars = m_AuxStreamType;
		((int*)uAuxPars)[1] = m_AuxPrivateConf;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbT2DemodAuxPars* uAuxPars)
	{
		m_AuxStreamType = *(int*)uAuxPars;
		m_AuxPrivateConf = ((int*)uAuxPars)[1];
	}

	public DtDvbT2DemodAuxPars()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int auxStreamType);
		m_AuxStreamType = auxStreamType;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int auxPrivateConf);
		m_AuxPrivateConf = auxPrivateConf;
	}
}
