using System.Collections.Generic;
using System.Runtime.CompilerServices;
using Dtapi;
using std;

namespace DTAPINET;

public class DtDvbC2DSlicePars
{
	public int m_Id;

	public int m_TunePosition;

	public int m_OffsetLeft;

	public int m_OffsetRight;

	public int m_TiDepth;

	public int m_Type;

	public int m_FecHdrType;

	public bool m_ConstConfig;

	public bool m_LeftNotch;

	public List<DtDvbC2PlpPars> m_Plps;

	internal DtDvbC2DSlicePars()
	{
		m_Plps = new List<DtDvbC2PlpPars>();
		Init(0);
	}

	internal unsafe void Init(int DSliceId)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2DSlicePars dtDvbC2DSlicePars);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2DSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DSlicePars, 32)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2DSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DSlicePars, 36)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2DSlicePars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DSlicePars, 40)) = 0;
		try
		{
			global::_003CModule_003E.Dtapi_002EDtDvbC2DSlicePars_002EInit(&dtDvbC2DSlicePars, DSliceId);
			ConvertFromUnmgd(&dtDvbC2DSlicePars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2DSlicePars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2DSlicePars_002E_007Bdtor_007D), &dtDvbC2DSlicePars);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2DSlicePars, 32)));
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbC2DSlicePars* uDSlicePars)
	{
		*(int*)uDSlicePars = m_Id;
		((int*)uDSlicePars)[1] = m_TunePosition;
		((int*)uDSlicePars)[2] = m_OffsetLeft;
		((int*)uDSlicePars)[3] = m_OffsetRight;
		((int*)uDSlicePars)[4] = m_TiDepth;
		((int*)uDSlicePars)[5] = m_Type;
		((int*)uDSlicePars)[6] = m_FecHdrType;
		((sbyte*)uDSlicePars)[28] = (m_ConstConfig ? ((sbyte)1) : ((sbyte)0));
		((sbyte*)uDSlicePars)[29] = (m_LeftNotch ? ((sbyte)1) : ((sbyte)0));
		Dtapi.DtDvbC2DSlicePars* ptr = (Dtapi.DtDvbC2DSlicePars*)((byte*)uDSlicePars + 32);
		vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)ptr;
		Dtapi.DtDvbC2PlpPars* last = (Dtapi.DtDvbC2PlpPars*)(int)((uint*)ptr2)[1];
		global::_003CModule_003E.std_002E_Destroy_range_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020Dtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E((Dtapi.DtDvbC2PlpPars*)(int)(*(uint*)ptr2), last, (allocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E*)ptr2);
		((int*)ptr2)[1] = *(int*)ptr2;
		int num = 0;
		if (0 >= m_Plps.Count)
		{
			return;
		}
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2PlpPars dtDvbC2PlpPars);
		do
		{
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 64)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 68)) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDvbC2PlpPars, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 72)) = 0;
			try
			{
				m_Plps[num].ConvertToUnmgd(&dtDvbC2PlpPars);
				global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E_002Eemplace_back_003Cstruct_0020Dtapi_003A_003ADtDvbC2PlpPars_0020const_0020_0026_003E((vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)ptr, &dtDvbC2PlpPars);
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2PlpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2PlpPars_002E_007Bdtor_007D), &dtDvbC2PlpPars);
				throw;
			}
			global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E_002E_Tidy((vector_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2XFecFrameHeader_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDvbC2PlpPars, 64)));
			num++;
		}
		while (num < m_Plps.Count);
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbC2DSlicePars* uDSlicePars)
	{
		m_Id = *(int*)uDSlicePars;
		m_TunePosition = ((int*)uDSlicePars)[1];
		m_OffsetLeft = ((int*)uDSlicePars)[2];
		m_OffsetRight = ((int*)uDSlicePars)[3];
		m_TiDepth = ((int*)uDSlicePars)[4];
		m_Type = ((int*)uDSlicePars)[5];
		m_FecHdrType = ((int*)uDSlicePars)[6];
		m_ConstConfig = ((bool*)uDSlicePars)[28];
		m_LeftNotch = ((bool*)uDSlicePars)[29];
		m_Plps.Clear();
		uint num = 0u;
		vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E* ptr = (vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)((byte*)uDSlicePars + 32);
		if (0u < (uint)((((int*)ptr)[1] - *(int*)ptr) / 92))
		{
			ptr = (vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)((byte*)uDSlicePars + 32);
			vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E* ptr2 = (vector_003CDtapi_003A_003ADtDvbC2PlpPars_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDvbC2PlpPars_003E_0020_003E*)((byte*)ptr + 4);
			int num2 = 0;
			do
			{
				DtDvbC2PlpPars dtDvbC2PlpPars = new DtDvbC2PlpPars();
				dtDvbC2PlpPars.ConvertFromUnmgd((Dtapi.DtDvbC2PlpPars*)(((int*)uDSlicePars)[8] + num2));
				m_Plps.Add(dtDvbC2PlpPars);
				num++;
				num2 += 92;
			}
			while (num < (uint)((*(int*)ptr2 - *(int*)ptr) / 92));
		}
	}
}
