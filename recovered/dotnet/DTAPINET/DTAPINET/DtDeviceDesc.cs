using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtDeviceDesc
{
	public int m_Category;

	public long m_Serial;

	public int m_PciBusNumber;

	public int m_SlotNumber;

	public int m_UsbAddress;

	public int m_TypeNumber;

	public int m_SubType;

	public int m_DeviceId;

	public int m_VendorId;

	public int m_SubsystemId;

	public int m_SubVendorId;

	public int m_NumHwFuncs;

	public int m_HardwareRevision;

	public int m_FirmwareVersion;

	public int m_FirmwareVariant;

	public Dtapi.DtFirmwareStatus m_FirmwareStatus;

	public readonly DtFwBuildDateTime m_FwBuildDate = new DtFwBuildDateTime();

	public int m_NumDtInpChan;

	public int m_NumDtOutpChan;

	public int m_NumPorts;

	public byte[] m_Ip;

	public byte[][] m_IpV6;

	public byte[] m_MacAddr;

	public int m_PcieNumLanes;

	public int m_PcieMaxLanes;

	public int m_PcieLinkSpeed;

	public int m_PcieMaxSpeed;

	public int m_PcieMaxPayloadSize;

	public int m_PcieMaxReadRequestSize;

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDeviceDesc* uDvcDesc)
	{
		m_Category = *(int*)uDvcDesc;
		m_Serial = ((long*)uDvcDesc)[1];
		m_PciBusNumber = ((int*)uDvcDesc)[4];
		m_SlotNumber = ((int*)uDvcDesc)[5];
		m_UsbAddress = ((int*)uDvcDesc)[6];
		m_TypeNumber = ((int*)uDvcDesc)[7];
		m_SubType = ((int*)uDvcDesc)[8];
		m_DeviceId = ((int*)uDvcDesc)[9];
		m_VendorId = ((int*)uDvcDesc)[10];
		m_SubsystemId = ((int*)uDvcDesc)[11];
		m_SubVendorId = ((int*)uDvcDesc)[12];
		m_HardwareRevision = ((int*)uDvcDesc)[14];
		m_FirmwareVersion = ((int*)uDvcDesc)[15];
		m_FirmwareVariant = ((int*)uDvcDesc)[16];
		m_FirmwareStatus = ((Dtapi.DtFirmwareStatus*)uDvcDesc)[17];
		m_FwBuildDate.m_Year = ((int*)uDvcDesc)[18];
		m_FwBuildDate.m_Month = ((int*)uDvcDesc)[19];
		m_FwBuildDate.m_Day = ((int*)uDvcDesc)[20];
		m_FwBuildDate.m_Hour = ((int*)uDvcDesc)[21];
		m_FwBuildDate.m_Minute = ((int*)uDvcDesc)[22];
		m_NumHwFuncs = ((int*)uDvcDesc)[13];
		m_NumDtInpChan = ((int*)uDvcDesc)[23];
		m_NumDtOutpChan = ((int*)uDvcDesc)[24];
		m_NumPorts = ((int*)uDvcDesc)[25];
		int num = 0;
		Dtapi.DtDeviceDesc* ptr = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 104);
		do
		{
			ref byte reference = ref m_Ip[num];
			reference = ((byte*)ptr)[num];
			num++;
		}
		while (num < 4);
		int num2 = 0;
		Dtapi.DtDeviceDesc* ptr2 = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 108);
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
			ptr2 = (Dtapi.DtDeviceDesc*)((byte*)ptr2 + 16);
		}
		while (num2 < 3);
		int num4 = 0;
		Dtapi.DtDeviceDesc* ptr3 = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 156);
		do
		{
			ref byte reference3 = ref m_MacAddr[num4];
			reference3 = ((byte*)ptr3)[num4];
			num4++;
		}
		while (num4 < 6);
		m_PcieNumLanes = ((int*)uDvcDesc)[41];
		m_PcieMaxLanes = ((int*)uDvcDesc)[42];
		m_PcieLinkSpeed = ((int*)uDvcDesc)[43];
		m_PcieMaxSpeed = ((int*)uDvcDesc)[44];
		m_PcieMaxPayloadSize = ((int*)uDvcDesc)[45];
		m_PcieMaxReadRequestSize = ((int*)uDvcDesc)[46];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtDeviceDesc* uDvcDesc)
	{
		*(int*)uDvcDesc = m_Category;
		((long*)uDvcDesc)[1] = m_Serial;
		((int*)uDvcDesc)[4] = m_PciBusNumber;
		((int*)uDvcDesc)[5] = m_SlotNumber;
		((int*)uDvcDesc)[6] = m_UsbAddress;
		((int*)uDvcDesc)[7] = m_TypeNumber;
		((int*)uDvcDesc)[8] = m_SubType;
		((int*)uDvcDesc)[9] = m_DeviceId;
		((int*)uDvcDesc)[10] = m_VendorId;
		((int*)uDvcDesc)[11] = m_SubsystemId;
		((int*)uDvcDesc)[12] = m_SubVendorId;
		((int*)uDvcDesc)[8] = m_SubType;
		((int*)uDvcDesc)[14] = m_HardwareRevision;
		((int*)uDvcDesc)[16] = m_FirmwareVariant;
		((int*)uDvcDesc)[17] = (int)m_FirmwareStatus;
		((int*)uDvcDesc)[18] = m_FwBuildDate.m_Year;
		((int*)uDvcDesc)[19] = m_FwBuildDate.m_Month;
		((int*)uDvcDesc)[20] = m_FwBuildDate.m_Day;
		((int*)uDvcDesc)[21] = m_FwBuildDate.m_Hour;
		((int*)uDvcDesc)[22] = m_FwBuildDate.m_Minute;
		((int*)uDvcDesc)[13] = m_NumHwFuncs;
		((int*)uDvcDesc)[23] = m_NumDtInpChan;
		((int*)uDvcDesc)[24] = m_NumDtOutpChan;
		((int*)uDvcDesc)[25] = m_NumPorts;
		int num = 0;
		Dtapi.DtDeviceDesc* ptr = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 104);
		do
		{
			((sbyte*)ptr)[num] = (sbyte)m_Ip[num];
			num++;
		}
		while (num < 4);
		int num2 = 0;
		Dtapi.DtDeviceDesc* ptr2 = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 108);
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
			ptr2 = (Dtapi.DtDeviceDesc*)((byte*)ptr2 + 16);
		}
		while (num2 < 3);
		int num4 = 0;
		Dtapi.DtDeviceDesc* ptr3 = (Dtapi.DtDeviceDesc*)((byte*)uDvcDesc + 156);
		do
		{
			((sbyte*)ptr3)[num4] = (sbyte)m_MacAddr[num4];
			num4++;
		}
		while (num4 < 6);
		((int*)uDvcDesc)[41] = m_PcieNumLanes;
		((int*)uDvcDesc)[42] = m_PcieMaxLanes;
		((int*)uDvcDesc)[43] = m_PcieLinkSpeed;
		((int*)uDvcDesc)[44] = m_PcieMaxSpeed;
		((int*)uDvcDesc)[45] = m_PcieMaxPayloadSize;
		((int*)uDvcDesc)[46] = m_PcieMaxReadRequestSize;
	}

	public unsafe DtDeviceDesc()
	{
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
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDeviceDesc dtDeviceDesc);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 72)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 76)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 80)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 84)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 88)) = 0;
		ConvertFromUnmgd(&dtDeviceDesc);
	}
}
