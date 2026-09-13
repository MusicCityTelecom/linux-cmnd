using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtHwFuncDesc
{
	public DtDeviceDesc m_DvcDesc;

	public int m_ChanType;

	public DtCaps m_Flags;

	public int m_IndexOnDvc;

	public int m_Port;

	public byte[] m_Ip;

	public byte[][] m_IpV6;

	public byte[] m_MacAddr;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtHwFuncDesc* uHwFuncDesc)
	{
		m_DvcDesc.ConvertFromUnmgd((Dtapi.DtDeviceDesc*)uHwFuncDesc);
		m_ChanType = ((int*)uHwFuncDesc)[48];
		m_Flags = new DtCaps((Dtapi.DtCaps*)((byte*)uHwFuncDesc + 200));
		m_IndexOnDvc = ((int*)uHwFuncDesc)[58];
		m_Port = ((int*)uHwFuncDesc)[59];
		int num = 0;
		Dtapi.DtHwFuncDesc* ptr = (Dtapi.DtHwFuncDesc*)((byte*)uHwFuncDesc + 240);
		do
		{
			ref byte reference = ref m_Ip[num];
			reference = ((byte*)ptr)[num];
			num++;
		}
		while (num < 4);
		int num2 = 0;
		Dtapi.DtHwFuncDesc* ptr2 = (Dtapi.DtHwFuncDesc*)((byte*)uHwFuncDesc + 244);
		do
		{
			int num3 = 0;
			do
			{
				ref byte reference2 = ref m_IpV6[num2][num3];
				reference2 = ((byte*)ptr2)[num3];
				num3++;
			}
			while (num3 < 16);
			num2++;
			ptr2 = (Dtapi.DtHwFuncDesc*)((byte*)ptr2 + 16);
		}
		while (num2 < 3);
		int num4 = 0;
		Dtapi.DtHwFuncDesc* ptr3 = (Dtapi.DtHwFuncDesc*)((byte*)uHwFuncDesc + 292);
		do
		{
			ref byte reference3 = ref m_MacAddr[num4];
			reference3 = ((byte*)ptr3)[num4];
			num4++;
		}
		while (num4 < 6);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtHwFuncDesc* uHwFunc)
	{
		m_DvcDesc.ConvertToUnmgd((Dtapi.DtDeviceDesc*)uHwFunc);
		((int*)uHwFunc)[48] = m_ChanType;
		// IL cpblk instruction
		System.Runtime.CompilerServices.Unsafe.CopyBlock((byte*)uHwFunc + 200, m_Flags.m_pCaps, 32);
		((int*)uHwFunc)[58] = m_IndexOnDvc;
		((int*)uHwFunc)[59] = m_Port;
		int num = 0;
		Dtapi.DtHwFuncDesc* ptr = (Dtapi.DtHwFuncDesc*)((byte*)uHwFunc + 240);
		do
		{
			((sbyte*)ptr)[num] = (sbyte)m_Ip[num];
			num++;
		}
		while (num < 4);
		int num2 = 0;
		Dtapi.DtHwFuncDesc* ptr2 = (Dtapi.DtHwFuncDesc*)((byte*)uHwFunc + 244);
		do
		{
			int num3 = 0;
			do
			{
				((sbyte*)ptr2)[num3] = (sbyte)m_IpV6[num2][num3];
				num3++;
			}
			while (num3 < 16);
			num2++;
			ptr2 = (Dtapi.DtHwFuncDesc*)((byte*)ptr2 + 16);
		}
		while (num2 < 3);
		int num4 = 0;
		Dtapi.DtHwFuncDesc* ptr3 = (Dtapi.DtHwFuncDesc*)((byte*)uHwFunc + 292);
		do
		{
			((sbyte*)ptr3)[num4] = (sbyte)m_MacAddr[num4];
			num4++;
		}
		while (num4 < 6);
	}

	public unsafe DtHwFuncDesc(Dtapi.DtHwFuncDesc* HwFuncDesc)
	{
		m_DvcDesc = new DtDeviceDesc();
		m_Ip = new byte[4];
		m_IpV6 = new byte[3][];
		int num = 0;
		do
		{
			m_IpV6[num] = new byte[16];
			num++;
		}
		while (num < 3);
		m_MacAddr = new byte[6];
		ConvertFromUnmgd(HwFuncDesc);
	}

	public unsafe DtHwFuncDesc()
	{
		m_DvcDesc = new DtDeviceDesc();
		m_Ip = new byte[4];
		m_IpV6 = new byte[3][];
		int num = 0;
		do
		{
			m_IpV6[num] = new byte[16];
			num++;
		}
		while (num < 3);
		m_MacAddr = new byte[6];
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtHwFuncDesc dtHwFuncDesc);
		global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D(&dtHwFuncDesc);
		ConvertFromUnmgd(&dtHwFuncDesc);
	}
}
