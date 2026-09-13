using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public struct DtIqDirectPars
{
	public DtFractionInt m_SampleRate;

	public int m_IqPacking;

	public int m_ChanFilter;

	public int m_Interpolation;

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIqDirectPars dtIqDirectPars);
		global::_003CModule_003E.Dtapi_002EDtIqDirectPars_002E_007Bctor_007D(&dtIqDirectPars);
		ConvertToUnmgd(&dtIqDirectPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIqDirectPars_002ECheckValidity(&dtIqDirectPars);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIqDirectPars* uIqDirectPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
		*(int*)(&dtFractionInt) = m_SampleRate.m_Num;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = m_SampleRate.m_Den;
		// IL cpblk instruction
		System.Runtime.CompilerServices.Unsafe.CopyBlock(uIqDirectPars, ref dtFractionInt, 8);
		((int*)uIqDirectPars)[2] = m_IqPacking;
		((int*)uIqDirectPars)[3] = m_ChanFilter;
		((int*)uIqDirectPars)[4] = m_Interpolation;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIqDirectPars* uIqDirectPars)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt sampleRate);
		sampleRate.m_Num = *(int*)uIqDirectPars;
		sampleRate.m_Den = ((int*)uIqDirectPars)[1];
		m_SampleRate = sampleRate;
		m_IqPacking = ((int*)uIqDirectPars)[2];
		m_ChanFilter = ((int*)uIqDirectPars)[3];
		m_Interpolation = ((int*)uIqDirectPars)[4];
	}
}
