using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtIpPars
{
	public byte[] m_Ip;

	public ushort m_Port;

	public byte[] m_SrcFltIp;

	public ushort m_SrcFltPort;

	public int m_VlanId;

	public int m_VlanPriority;

	public byte[] m_Ip2;

	public ushort m_Port2;

	public byte[] m_SrcFltIp2;

	public ushort m_SrcFltPort2;

	public int m_VlanId2;

	public int m_VlanPriority2;

	public int m_TimeToLive;

	public int m_NumTpPerIp;

	public int m_Protocol;

	public int m_DiffServ;

	public int m_FecMode;

	public int m_FecNumRows;

	public int m_FecNumCols;

	public int m_Flags;

	public int m_Mode;

	public DtIpProfile m_IpProfile;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtIpPars* uTsIpPars)
	{
		int num = 0;
		do
		{
			ref byte reference = ref m_Ip[num];
			reference = *(num + (byte*)uTsIpPars);
			num++;
		}
		while (num < 16);
		m_Port = ((ushort*)uTsIpPars)[8];
		int num2 = 0;
		Dtapi.DtIpPars* ptr = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 18);
		do
		{
			ref byte reference2 = ref m_SrcFltIp[num2];
			reference2 = ((byte*)ptr)[num2];
			num2++;
		}
		while (num2 < 16);
		m_SrcFltPort = ((ushort*)uTsIpPars)[17];
		m_VlanId = ((int*)uTsIpPars)[9];
		m_VlanPriority = ((int*)uTsIpPars)[10];
		int num3 = 0;
		Dtapi.DtIpPars* ptr2 = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 44);
		do
		{
			ref byte reference3 = ref m_Ip2[num3];
			reference3 = ((byte*)ptr2)[num3];
			num3++;
		}
		while (num3 < 16);
		m_Port2 = ((ushort*)uTsIpPars)[30];
		int num4 = 0;
		Dtapi.DtIpPars* ptr3 = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 62);
		do
		{
			ref byte reference4 = ref m_SrcFltIp2[num4];
			reference4 = ((byte*)ptr3)[num4];
			num4++;
		}
		while (num4 < 16);
		m_SrcFltPort2 = ((ushort*)uTsIpPars)[39];
		m_VlanId2 = ((int*)uTsIpPars)[20];
		m_VlanPriority2 = ((int*)uTsIpPars)[21];
		m_TimeToLive = ((int*)uTsIpPars)[22];
		m_TimeToLive = ((int*)uTsIpPars)[22];
		m_NumTpPerIp = ((int*)uTsIpPars)[23];
		m_Protocol = ((int*)uTsIpPars)[24];
		m_DiffServ = ((int*)uTsIpPars)[25];
		m_FecMode = ((int*)uTsIpPars)[26];
		m_FecNumCols = ((int*)uTsIpPars)[28];
		m_FecNumRows = ((int*)uTsIpPars)[27];
		m_Flags = ((int*)uTsIpPars)[29];
		m_Mode = ((int*)uTsIpPars)[30];
		m_IpProfile.m_Profile = ((int*)uTsIpPars)[31];
		m_IpProfile.m_MaxBitrate = ((uint*)uTsIpPars)[32];
		m_IpProfile.m_MaxSkew = ((int*)uTsIpPars)[33];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtIpPars* uTsIpPars)
	{
		int num = 0;
		do
		{
			*(num + (sbyte*)uTsIpPars) = (sbyte)m_Ip[num];
			num++;
		}
		while (num < 16);
		((short*)uTsIpPars)[8] = (short)m_Port;
		int num2 = 0;
		Dtapi.DtIpPars* ptr = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 18);
		do
		{
			((sbyte*)ptr)[num2] = (sbyte)m_SrcFltIp[num2];
			num2++;
		}
		while (num2 < 16);
		((short*)uTsIpPars)[17] = (short)m_SrcFltPort;
		((int*)uTsIpPars)[9] = m_VlanId;
		((int*)uTsIpPars)[10] = m_VlanPriority;
		int num3 = 0;
		Dtapi.DtIpPars* ptr2 = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 44);
		do
		{
			((sbyte*)ptr2)[num3] = (sbyte)m_Ip2[num3];
			num3++;
		}
		while (num3 < 16);
		((short*)uTsIpPars)[30] = (short)m_Port2;
		int num4 = 0;
		Dtapi.DtIpPars* ptr3 = (Dtapi.DtIpPars*)((byte*)uTsIpPars + 62);
		do
		{
			((sbyte*)ptr3)[num4] = (sbyte)m_SrcFltIp2[num4];
			num4++;
		}
		while (num4 < 16);
		((short*)uTsIpPars)[39] = (short)m_SrcFltPort2;
		((int*)uTsIpPars)[20] = m_VlanId2;
		((int*)uTsIpPars)[21] = m_VlanPriority2;
		((int*)uTsIpPars)[22] = m_TimeToLive;
		((int*)uTsIpPars)[22] = m_TimeToLive;
		((int*)uTsIpPars)[23] = m_NumTpPerIp;
		((int*)uTsIpPars)[24] = m_Protocol;
		((int*)uTsIpPars)[25] = m_DiffServ;
		((int*)uTsIpPars)[26] = m_FecMode;
		((int*)uTsIpPars)[28] = m_FecNumCols;
		((int*)uTsIpPars)[27] = m_FecNumRows;
		((int*)uTsIpPars)[29] = m_Flags;
		((int*)uTsIpPars)[30] = m_Mode;
		((int*)uTsIpPars)[31] = m_IpProfile.m_Profile;
		((int*)uTsIpPars)[32] = (int)m_IpProfile.m_MaxBitrate;
		((int*)uTsIpPars)[33] = m_IpProfile.m_MaxSkew;
	}

	public unsafe DtIpPars()
	{
		m_Ip = new byte[16];
		m_SrcFltIp = new byte[16];
		m_Ip2 = new byte[16];
		m_SrcFltIp2 = new byte[16];
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		try
		{
			ConvertFromUnmgd(&dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
	}
}
