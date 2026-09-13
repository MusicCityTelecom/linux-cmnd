using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtAtsc3PlpPars
{
	public int m_Id;

	public bool m_LlsFlag;

	public int m_Layer;

	public int m_Modulation;

	public int m_CodeRate;

	public int m_FecCodeLength;

	public int m_FecOuterCode;

	public int m_LdmInjectLevel;

	public bool m_BbFrameCounter;

	public int m_PlpType;

	public int m_NumSubslices;

	public int m_SubsliceInterval;

	public int m_TiMode;

	public int m_CtiDepth;

	public bool m_TiExtInterleaving;

	public bool m_HtiInterSubframe;

	public int m_HtiNumTiBlocks;

	public int m_HtiNumFecBlocksMax;

	public bool m_HtiCellInterleaver;

	public int m_CoreLayerPlpId;

	public int m_HtiNumFecBlocks;

	public int m_PlpSize;

	public int m_PlpStart;

	public unsafe void Init(int plpId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002EInit(&dtAtsc3PlpPars, plpId);
		ConvertFromUnmgd(&dtAtsc3PlpPars);
	}

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002EInit(&dtAtsc3PlpPars, 0);
		ConvertFromUnmgd(&dtAtsc3PlpPars);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtAtsc3PlpPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars2);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars2);
		ConvertToUnmgd(&dtAtsc3PlpPars);
		Rhs.ConvertToUnmgd(&dtAtsc3PlpPars2);
		return global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_003D_003D(&dtAtsc3PlpPars, &dtAtsc3PlpPars2);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtAtsc3PlpPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	[SpecialName]
	public unsafe DtAtsc3PlpPars op_Assign(DtAtsc3PlpPars Rhs)
	{
		if (this != Rhs)
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
			global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
			Rhs.ConvertToUnmgd(&dtAtsc3PlpPars);
			ConvertFromUnmgd(&dtAtsc3PlpPars);
		}
		return this;
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtAtsc3PlpPars* uA3Pars)
	{
		*(int*)uA3Pars = m_Id;
		((sbyte*)uA3Pars)[4] = (m_LlsFlag ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[2] = m_Layer;
		((int*)uA3Pars)[3] = m_Modulation;
		((int*)uA3Pars)[4] = m_CodeRate;
		((int*)uA3Pars)[5] = m_FecCodeLength;
		((int*)uA3Pars)[6] = m_FecOuterCode;
		((int*)uA3Pars)[7] = m_LdmInjectLevel;
		((sbyte*)uA3Pars)[32] = (m_BbFrameCounter ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[9] = m_PlpType;
		((int*)uA3Pars)[10] = m_NumSubslices;
		((int*)uA3Pars)[11] = m_SubsliceInterval;
		((int*)uA3Pars)[12] = m_TiMode;
		((int*)uA3Pars)[13] = m_CtiDepth;
		((sbyte*)uA3Pars)[56] = (m_TiExtInterleaving ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uA3Pars)[57] = (m_HtiInterSubframe ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[15] = m_HtiNumTiBlocks;
		((int*)uA3Pars)[16] = m_HtiNumFecBlocksMax;
		((sbyte*)uA3Pars)[68] = (m_HtiCellInterleaver ? ((sbyte)1) : ((sbyte)0));
		((int*)uA3Pars)[27] = m_CoreLayerPlpId;
		((int*)uA3Pars)[28] = m_HtiNumFecBlocks;
		((int*)uA3Pars)[29] = m_PlpSize;
		((int*)uA3Pars)[30] = m_PlpStart;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtAtsc3PlpPars* uA3Pars)
	{
		m_Id = *(int*)uA3Pars;
		m_LlsFlag = ((bool*)uA3Pars)[4];
		m_Layer = ((int*)uA3Pars)[2];
		m_Modulation = ((int*)uA3Pars)[3];
		m_CodeRate = ((int*)uA3Pars)[4];
		m_FecCodeLength = ((int*)uA3Pars)[5];
		m_FecOuterCode = ((int*)uA3Pars)[6];
		m_LdmInjectLevel = ((int*)uA3Pars)[7];
		m_BbFrameCounter = ((bool*)uA3Pars)[32];
		m_PlpType = ((int*)uA3Pars)[9];
		m_NumSubslices = ((int*)uA3Pars)[10];
		m_SubsliceInterval = ((int*)uA3Pars)[11];
		m_TiMode = ((int*)uA3Pars)[12];
		m_CtiDepth = ((int*)uA3Pars)[13];
		m_TiExtInterleaving = ((bool*)uA3Pars)[56];
		m_HtiInterSubframe = ((bool*)uA3Pars)[57];
		m_HtiNumTiBlocks = ((int*)uA3Pars)[15];
		m_HtiNumFecBlocksMax = ((int*)uA3Pars)[16];
		m_HtiCellInterleaver = ((bool*)uA3Pars)[68];
		m_CoreLayerPlpId = ((int*)uA3Pars)[27];
		m_HtiNumFecBlocks = ((int*)uA3Pars)[28];
		m_PlpSize = ((int*)uA3Pars)[29];
		m_PlpStart = ((int*)uA3Pars)[30];
	}

	public unsafe DtAtsc3PlpPars()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtAtsc3PlpPars dtAtsc3PlpPars);
		global::_003CModule_003E.Dtapi_002EDtAtsc3PlpPars_002E_007Bctor_007D(&dtAtsc3PlpPars);
		ConvertFromUnmgd(&dtAtsc3PlpPars);
	}
}
