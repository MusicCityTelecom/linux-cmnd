using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtIsdbsPars
{
	public bool m_DoMux;

	public int m_Emergency;

	public int[] m_RelTs2TsId;

	public int[] m_Slot2RelTs;

	public DtIsdbsLayerPars[] m_LayerPars;

	public unsafe DTAPI_RESULT CheckValidity()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
		*(sbyte*)(&dtIsdbsPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
		ConvertToUnmgd(&dtIsdbsPars);
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtIsdbsPars_002ECheckValidity(&dtIsdbsPars);
	}

	public unsafe void Init()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
		*(sbyte*)(&dtIsdbsPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
		global::_003CModule_003E.Dtapi_002EDtIsdbsPars_002EInit(&dtIsdbsPars);
		ConvertFromUnmgd(&dtIsdbsPars);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtIsdbsPars Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
		*(sbyte*)(&dtIsdbsPars) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
		ConvertToUnmgd(&dtIsdbsPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars2);
		*(sbyte*)(&dtIsdbsPars2) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars2, 1)) = 0;
		Rhs.ConvertToUnmgd(&dtIsdbsPars2);
		return global::_003CModule_003E.Dtapi_002EDtIsdbsPars_002E_003D_003D(&dtIsdbsPars, &dtIsdbsPars2);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtIsdbsPars Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIsdbsPars* uIsdbsPars)
	{
		*(bool*)uIsdbsPars = m_DoMux;
		((int*)uIsdbsPars)[1] = m_Emergency;
		int num = 0;
		int[] relTs2TsId = m_RelTs2TsId;
		if (0 < (nint)relTs2TsId.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 8);
			do
			{
				*(int*)ptr = relTs2TsId[num];
				num++;
				ptr = (Dtapi.DtIsdbsPars*)((byte*)ptr + 4);
				relTs2TsId = m_RelTs2TsId;
			}
			while (num < (nint)relTs2TsId.LongLength);
		}
		int num2 = 0;
		int[] slot2RelTs = m_Slot2RelTs;
		if (0 < (nint)slot2RelTs.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr2 = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 40);
			do
			{
				*(int*)ptr2 = slot2RelTs[num2];
				num2++;
				ptr2 = (Dtapi.DtIsdbsPars*)((byte*)ptr2 + 4);
				slot2RelTs = m_Slot2RelTs;
			}
			while (num2 < (nint)slot2RelTs.LongLength);
		}
		int num3 = 0;
		DtIsdbsLayerPars[] layerPars = m_LayerPars;
		if (0 < (nint)layerPars.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr3 = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 236);
			do
			{
				*((int*)ptr3 - 1) = layerPars[num3].m_NumSlots;
				*(int*)ptr3 = m_LayerPars[num3].m_ModCod;
				num3++;
				ptr3 = (Dtapi.DtIsdbsPars*)((byte*)ptr3 + 8);
				layerPars = m_LayerPars;
			}
			while (num3 < (nint)layerPars.LongLength);
		}
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIsdbsPars* uIsdbsPars)
	{
		m_DoMux = *(bool*)uIsdbsPars;
		m_Emergency = ((int*)uIsdbsPars)[1];
		int num = 0;
		if (0 < (nint)m_RelTs2TsId.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 8);
			do
			{
				ref int reference = ref m_RelTs2TsId[num];
				reference = *(int*)ptr;
				num++;
				ptr = (Dtapi.DtIsdbsPars*)((byte*)ptr + 4);
			}
			while (num < (nint)m_RelTs2TsId.LongLength);
		}
		int num2 = 0;
		if (0 < (nint)m_Slot2RelTs.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr2 = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 40);
			do
			{
				ref int reference2 = ref m_Slot2RelTs[num2];
				reference2 = *(int*)ptr2;
				num2++;
				ptr2 = (Dtapi.DtIsdbsPars*)((byte*)ptr2 + 4);
			}
			while (num2 < (nint)m_Slot2RelTs.LongLength);
		}
		int num3 = 0;
		if (0 < (nint)m_LayerPars.LongLength)
		{
			Dtapi.DtIsdbsPars* ptr3 = (Dtapi.DtIsdbsPars*)((byte*)uIsdbsPars + 236);
			do
			{
				m_LayerPars[num3].m_NumSlots = *((int*)ptr3 - 1);
				m_LayerPars[num3].m_ModCod = *(int*)ptr3;
				num3++;
				ptr3 = (Dtapi.DtIsdbsPars*)((byte*)ptr3 + 8);
			}
			while (num3 < (nint)m_LayerPars.LongLength);
		}
	}

	public DtIsdbsPars()
	{
		m_DoMux = false;
		m_RelTs2TsId = new int[8];
		m_Slot2RelTs = new int[48];
		m_LayerPars = new DtIsdbsLayerPars[4];
	}
}
