using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using _003CCppImplementationDetails_003E;
using Dtapi;
using std;

namespace DTAPINET;

public class DtGlobal
{
	public static DTAPI_RESULT DtapiCheckDeviceDriverVersion(int DvcCategory)
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtapiCheckDeviceDriverVersion(DvcCategory);
	}

	public static DTAPI_RESULT DtapiCheckDeviceDriverVersion()
	{
		return (DTAPI_RESULT)global::_003CModule_003E.Dtapi_002EDtapiCheckDeviceDriverVersion();
	}

	public unsafe static DTAPI_RESULT DtapiDeviceScan(int NumEntries, ref int NumEntriesResult, DtDeviceDesc[] rDvcDescArr, [MarshalAs(UnmanagedType.U1)] bool InclIpDvcs, int ScanOrder)
	{
		Dtapi.DtDeviceDesc* ptr = (Dtapi.DtDeviceDesc*)global::_003CModule_003E.new_005B_005D(((uint)NumEntries > 22369621u) ? uint.MaxValue : ((uint)(NumEntries * 192)));
		Dtapi.DtDeviceDesc* ptr3;
		try
		{
			if (ptr != null)
			{
				uint num = (uint)NumEntries;
				if (NumEntries != 0)
				{
					Dtapi.DtDeviceDesc* ptr2 = (Dtapi.DtDeviceDesc*)((byte*)ptr + 80);
					do
					{
						num--;
						*((int*)ptr2 - 2) = 0;
						*((int*)ptr2 - 1) = 0;
						*(int*)ptr2 = 0;
						((int*)ptr2)[1] = 0;
						((int*)ptr2)[2] = 0;
						ptr2 = (Dtapi.DtDeviceDesc*)((byte*)ptr2 + 192);
					}
					while (num != 0);
				}
				ptr3 = ptr;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num2 = (uint)NumEntries;
			uint num3 = ((num2 > 22369621) ? uint.MaxValue : (num2 * 192));
			global::_003CModule_003E.delete_005B_005D(ptr, num3);
			throw;
		}
		int num4 = NumEntriesResult;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiDeviceScan(NumEntries, &num4, ptr3, InclIpDvcs, ScanOrder);
		int num5 = Math.Min(NumEntries, num4);
		int num6 = 0;
		if (0 < num5)
		{
			Dtapi.DtDeviceDesc* ptr4 = ptr3;
			do
			{
				rDvcDescArr[num6] = new DtDeviceDesc();
				rDvcDescArr[num6].ConvertFromUnmgd(ptr4);
				num6++;
				ptr4 = (Dtapi.DtDeviceDesc*)((byte*)ptr4 + 192);
			}
			while (num6 < num5);
		}
		global::_003CModule_003E.delete_005B_005D(ptr3);
		NumEntriesResult = num4;
		return (DTAPI_RESULT)result;
	}

	public static DTAPI_RESULT DtapiDeviceScan(int NumEntries, ref int NumEntriesResult, DtDeviceDesc[] rDvcDescArr, [MarshalAs(UnmanagedType.U1)] bool InclIpDvcs)
	{
		return DtapiDeviceScan(NumEntries, ref NumEntriesResult, rDvcDescArr, InclIpDvcs, 0);
	}

	public static DTAPI_RESULT DtapiDeviceScan(int NumEntries, ref int NumEntriesResult, DtDeviceDesc[] rDvcDescArr)
	{
		return DtapiDeviceScan(NumEntries, ref NumEntriesResult, rDvcDescArr, InclIpDvcs: false, 0);
	}

	public unsafe static DTAPI_RESULT DtapiDtaPlusDeviceScan(int NumEntries, ref int NumEntriesResult, DtDtaPlusDeviceDesc[] rDvcDescArr)
	{
		uint num2;
		if ((uint)NumEntries <= 134217727u)
		{
			uint num = (uint)(NumEntries * 32);
			if (num <= 4294967291u)
			{
				num2 = num + 4;
				goto IL_001f;
			}
		}
		num2 = uint.MaxValue;
		goto IL_001f;
		IL_001f:
		Dtapi.DtDtaPlusDeviceDesc* ptr = (Dtapi.DtDtaPlusDeviceDesc*)global::_003CModule_003E.new_005B_005D(num2);
		Dtapi.DtDtaPlusDeviceDesc* ptr3;
		if (ptr != null)
		{
			*(int*)ptr = NumEntries;
			Dtapi.DtDtaPlusDeviceDesc* ptr2 = (Dtapi.DtDtaPlusDeviceDesc*)((byte*)ptr + 4);
			global::_003CModule_003E.__ehvec_ctor(ptr2, 32u, (uint)NumEntries, (delegate*<void*, void>)(delegate*<Dtapi.DtDtaPlusDeviceDesc*, Dtapi.DtDtaPlusDeviceDesc*>)(&global::_003CModule_003E.Dtapi_002EDtDtaPlusDeviceDesc_002E_007Bctor_007D), (delegate*<void*, void>)(delegate*<Dtapi.DtDtaPlusDeviceDesc*, void>)(&global::_003CModule_003E.Dtapi_002EDtDtaPlusDeviceDesc_002E_007Bdtor_007D));
			ptr3 = ptr2;
		}
		else
		{
			ptr3 = null;
		}
		int num3 = NumEntriesResult;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiDtaPlusDeviceScan(NumEntries, &num3, ptr3);
		int num4 = Math.Min(NumEntries, num3);
		int num5 = 0;
		if (0 < num4)
		{
			Dtapi.DtDtaPlusDeviceDesc* ptr4 = ptr3;
			do
			{
				rDvcDescArr[num5] = new DtDtaPlusDeviceDesc();
				rDvcDescArr[num5].ConvertFromUnmgd(ptr4);
				num5++;
				ptr4 = (Dtapi.DtDtaPlusDeviceDesc*)((byte*)ptr4 + 32);
			}
			while (num5 < num4);
		}
		if (ptr3 != null)
		{
			global::_003CModule_003E.Dtapi_002EDtDtaPlusDeviceDesc_002E__vecDelDtor(ptr3, 3u);
		}
		NumEntriesResult = num3;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiDtDeviceDesc2String(DtDeviceDesc rDvcDesc, int StringType, ref string rString, int MaxStringLength)
	{
		char* ptr = (char*)global::_003CModule_003E.new_005B_005D(((uint)MaxStringLength > 2147483647u) ? uint.MaxValue : ((uint)(MaxStringLength << 1)));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDeviceDesc dtDeviceDesc);
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 72)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 76)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 80)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 84)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtDeviceDesc, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtDeviceDesc, 88)) = 0;
		rDvcDesc.ConvertToUnmgd(&dtDeviceDesc);
		uint result = global::_003CModule_003E.Dtapi_002EDtapiDtDeviceDesc2String(&dtDeviceDesc, StringType, ptr, MaxStringLength);
		rString = new string(ptr);
		global::_003CModule_003E.delete_005B_005D(ptr);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiDtHwFuncDesc2String(DtHwFuncDesc rHwFunc, int StringType, ref string rString, int MaxStringLength)
	{
		char* ptr = (char*)global::_003CModule_003E.new_005B_005D(((uint)MaxStringLength > 2147483647u) ? uint.MaxValue : ((uint)(MaxStringLength << 1)));
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtHwFuncDesc dtHwFuncDesc);
		global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D(&dtHwFuncDesc);
		rHwFunc.ConvertToUnmgd(&dtHwFuncDesc);
		uint result = global::_003CModule_003E.Dtapi_002EDtapiDtHwFuncDesc2String(&dtHwFuncDesc, StringType, ptr, MaxStringLength);
		rString = new string(ptr);
		global::_003CModule_003E.delete_005B_005D(ptr);
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT DtapiGetDeviceDriverVersion(int DeviceCategory, ref List<DtDriverVersionInfo> DriverVersions)
	{
		DriverVersions = new List<DtDriverVersionInfo>();
		System.Runtime.CompilerServices.Unsafe.SkipInit(out vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E obj);
		*(int*)(&obj) = 0;
		System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) = 0;
		System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 8)) = 0;
		uint num;
		try
		{
			num = global::_003CModule_003E.Dtapi_002EDtapiGetDeviceDriverVersion(DeviceCategory, &obj);
			if (num == 0)
			{
				int num2 = 0;
				if (0 < (System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) - *(int*)(&obj)) / 48)
				{
					int num3 = 0;
					do
					{
						DtDriverVersionInfo dtDriverVersionInfo = new DtDriverVersionInfo();
						dtDriverVersionInfo.ConvertFromUnmgd((Dtapi.DtDriverVersionInfo*)(num3 + *(int*)(&obj)));
						DriverVersions.Add(dtDriverVersionInfo);
						num2++;
						num3 += 48;
					}
					while (num2 < (System.Runtime.CompilerServices.Unsafe.As<vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref obj, 4)) - *(int*)(&obj)) / 48);
				}
			}
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<vector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E_002E_007Bdtor_007D), &obj);
			throw;
		}
		global::_003CModule_003E.std_002Evector_003CDtapi_003A_003ADtDriverVersionInfo_002Cstd_003A_003Aallocator_003CDtapi_003A_003ADtDriverVersionInfo_003E_0020_003E_002E_Tidy(&obj);
		return (DTAPI_RESULT)num;
	}

	public unsafe static DTAPI_RESULT DtapiGetDeviceDriverVersion(int DvcCategory, ref int DriverVersionMajor, ref int DriverVersionMinor, ref int DriverVersionBugFix, ref int DriverVersionBuild)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtapiGetDeviceDriverVersion(DvcCategory, &num, &num2, &num3, &num4);
		DriverVersionMajor = num;
		DriverVersionMinor = num2;
		DriverVersionBugFix = num3;
		DriverVersionBuild = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiGetDtapiServiceVersion(ref int DriverVersionMajor, ref int DriverVersionMinor, ref int DriverVersionBugFix, ref int DriverVersionBuild)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtapiGetDtapiServiceVersion(&num, &num2, &num3, &num4);
		DriverVersionMajor = num;
		DriverVersionMinor = num2;
		DriverVersionBugFix = num3;
		DriverVersionBuild = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiGetVersion(ref int LibVersion, ref int LibVersionMinor, ref int LibVersionBugFix, ref int LibVersionBuild)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num2);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out int num4);
		uint result = global::_003CModule_003E.Dtapi_002EDtapiGetVersion(&num, &num2, &num3, &num4);
		LibVersion = num;
		LibVersionMinor = num2;
		LibVersionBugFix = num3;
		LibVersionBuild = num4;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiHwFuncScan(int NumEntries, ref int NumEntriesResult, DtHwFuncDesc[] rHwFuncs, [MarshalAs(UnmanagedType.U1)] bool InclIpDvcs, int ScanOrder)
	{
		Dtapi.DtHwFuncDesc* ptr = (Dtapi.DtHwFuncDesc*)global::_003CModule_003E.new_005B_005D(((uint)NumEntries > 14128181u) ? uint.MaxValue : ((uint)(NumEntries * 304)));
		Dtapi.DtHwFuncDesc* ptr3;
		try
		{
			if (ptr != null)
			{
				uint num = (uint)NumEntries;
				void* ptr2 = ptr;
				if (NumEntries != 0)
				{
					do
					{
						num--;
						global::_003CModule_003E.Dtapi_002EDtHwFuncDesc_002E_007Bctor_007D((Dtapi.DtHwFuncDesc*)ptr2);
						ptr2 = (byte*)ptr2 + 304;
					}
					while (num != 0);
				}
				ptr3 = ptr;
			}
			else
			{
				ptr3 = null;
			}
		}
		catch
		{
			//try-fault
			uint num2 = (uint)NumEntries;
			uint num3 = ((num2 > 14128181) ? uint.MaxValue : (num2 * 304));
			global::_003CModule_003E.delete_005B_005D(ptr, num3);
			throw;
		}
		int num4 = NumEntriesResult;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiHwFuncScan(NumEntries, &num4, ptr3, InclIpDvcs, ScanOrder);
		int num5 = Math.Min(NumEntries, num4);
		int num6 = 0;
		if (0 < num5)
		{
			Dtapi.DtHwFuncDesc* ptr4 = ptr3;
			do
			{
				rHwFuncs[num6] = new DtHwFuncDesc();
				rHwFuncs[num6].ConvertFromUnmgd(ptr4);
				num6++;
				ptr4 = (Dtapi.DtHwFuncDesc*)((byte*)ptr4 + 304);
			}
			while (num6 < num5);
		}
		global::_003CModule_003E.delete_005B_005D(ptr3);
		NumEntriesResult = num4;
		return (DTAPI_RESULT)result;
	}

	public static DTAPI_RESULT DtapiHwFuncScan(int NumEntries, ref int NumEntriesResult, DtHwFuncDesc[] rHwFuncs, [MarshalAs(UnmanagedType.U1)] bool InclIpDvcs)
	{
		return DtapiHwFuncScan(NumEntries, ref NumEntriesResult, rHwFuncs, InclIpDvcs, 0);
	}

	public static DTAPI_RESULT DtapiHwFuncScan(int NumEntries, ref int NumEntriesResult, DtHwFuncDesc[] rHwFuncs)
	{
		return DtapiHwFuncScan(NumEntries, ref NumEntriesResult, rHwFuncs, InclIpDvcs: false, 0);
	}

	public unsafe static DTAPI_RESULT DtapiModPars2Bandwidth(ref int ModBandwidth, ref int TotalBandwidth, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, object ModPars, int SymRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
				*(sbyte*)(&dtIsdbsPars) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
				global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
				try
				{
					void* ptr = null;
					if (ModPars == null)
					{
						ptr = null;
					}
					else if (ModPars.GetType() == typeof(DtCmmbPars))
					{
						((DtCmmbPars)ModPars).ConvertToUnmgd(&dtCmmbPars);
						ptr = &dtCmmbPars;
					}
					else if (ModPars.GetType() == typeof(DtDvbC2Pars))
					{
						((DtDvbC2Pars)ModPars).ConvertToUnmgd(&dtDvbC2Pars);
						ptr = &dtDvbC2Pars;
					}
					else if (ModPars.GetType() == typeof(DtDvbT2Pars))
					{
						((DtDvbT2Pars)ModPars).ConvertToUnmgd(&dtDvbT2Pars);
						ptr = &dtDvbT2Pars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbsPars))
					{
						((DtIsdbsPars)ModPars).ConvertToUnmgd(&dtIsdbsPars);
						ptr = &dtIsdbsPars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbtPars))
					{
						((DtIsdbtPars)ModPars).ConvertToUnmgd(&dtIsdbtPars);
						ptr = &dtIsdbtPars;
					}
					int num = 0;
					int num2 = 0;
					result = global::_003CModule_003E.Dtapi_002EDtapiModPars2Bandwidth(&num, &num2, ModType, XtraPar0, XtraPar1, XtraPar2, ptr, SymRate);
					ModBandwidth = num;
					TotalBandwidth = num2;
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
					throw;
				}
				map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
				try
				{
					global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2SymRate(ref int SymRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, object ModPars, DtFractionInt TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
				*(sbyte*)(&dtIsdbsPars) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
				global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
				try
				{
					void* ptr = null;
					if (ModPars == null)
					{
						ptr = null;
					}
					else if (ModPars.GetType() == typeof(DtCmmbPars))
					{
						((DtCmmbPars)ModPars).ConvertToUnmgd(&dtCmmbPars);
						ptr = &dtCmmbPars;
					}
					else if (ModPars.GetType() == typeof(DtDvbC2Pars))
					{
						((DtDvbC2Pars)ModPars).ConvertToUnmgd(&dtDvbC2Pars);
						ptr = &dtDvbC2Pars;
					}
					else if (ModPars.GetType() == typeof(DtDvbT2Pars))
					{
						((DtDvbT2Pars)ModPars).ConvertToUnmgd(&dtDvbT2Pars);
						ptr = &dtDvbT2Pars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbsPars))
					{
						((DtIsdbsPars)ModPars).ConvertToUnmgd(&dtIsdbsPars);
						ptr = &dtIsdbsPars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbtPars))
					{
						((DtIsdbtPars)ModPars).ConvertToUnmgd(&dtIsdbtPars);
						ptr = &dtIsdbtPars;
					}
					int num = 0;
					int den = TsRate.m_Den;
					System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
					*(int*)(&dtFractionInt) = TsRate.m_Num;
					System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = den;
					result = global::_003CModule_003E.Dtapi_002EDtapiModPars2SymRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, ptr, dtFractionInt);
					SymRate = num;
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
					throw;
				}
				map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
				try
				{
					global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2SymRate(ref int SymRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, object ModPars, int TsRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
				*(sbyte*)(&dtIsdbsPars) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
				global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
				try
				{
					void* ptr = null;
					if (ModPars == null)
					{
						ptr = null;
					}
					else if (ModPars.GetType() == typeof(DtCmmbPars))
					{
						((DtCmmbPars)ModPars).ConvertToUnmgd(&dtCmmbPars);
						ptr = &dtCmmbPars;
					}
					else if (ModPars.GetType() == typeof(DtDvbC2Pars))
					{
						((DtDvbC2Pars)ModPars).ConvertToUnmgd(&dtDvbC2Pars);
						ptr = &dtDvbC2Pars;
					}
					else if (ModPars.GetType() == typeof(DtDvbT2Pars))
					{
						((DtDvbT2Pars)ModPars).ConvertToUnmgd(&dtDvbT2Pars);
						ptr = &dtDvbT2Pars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbsPars))
					{
						((DtIsdbsPars)ModPars).ConvertToUnmgd(&dtIsdbsPars);
						ptr = &dtIsdbsPars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbtPars))
					{
						((DtIsdbtPars)ModPars).ConvertToUnmgd(&dtIsdbtPars);
						ptr = &dtIsdbtPars;
					}
					int num = 0;
					result = global::_003CModule_003E.Dtapi_002EDtapiModPars2SymRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, ptr, TsRate);
					SymRate = num;
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
					throw;
				}
				map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
				try
				{
					global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2SymRate(ref int SymRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, DtFractionInt TsRate)
	{
		int num = 0;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
		*(int*)(&dtFractionInt) = TsRate.m_Num;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = TsRate.m_Den;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiModPars2SymRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, dtFractionInt);
		SymRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2SymRate(ref int SymRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, int TsRate)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiModPars2SymRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, TsRate);
		SymRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, object ModPars, int SymRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
				*(sbyte*)(&dtIsdbsPars) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
				global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
				try
				{
					void* ptr = null;
					if (ModPars == null)
					{
						ptr = null;
					}
					else if (ModPars.GetType() == typeof(DtCmmbPars))
					{
						((DtCmmbPars)ModPars).ConvertToUnmgd(&dtCmmbPars);
						ptr = &dtCmmbPars;
					}
					else if (ModPars.GetType() == typeof(DtDvbC2Pars))
					{
						((DtDvbC2Pars)ModPars).ConvertToUnmgd(&dtDvbC2Pars);
						ptr = &dtDvbC2Pars;
					}
					else if (ModPars.GetType() == typeof(DtDvbT2Pars))
					{
						((DtDvbT2Pars)ModPars).ConvertToUnmgd(&dtDvbT2Pars);
						ptr = &dtDvbT2Pars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbsPars))
					{
						((DtIsdbsPars)ModPars).ConvertToUnmgd(&dtIsdbsPars);
						ptr = &dtIsdbsPars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbtPars))
					{
						((DtIsdbtPars)ModPars).ConvertToUnmgd(&dtIsdbtPars);
						ptr = &dtIsdbtPars;
					}
					System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
					*(int*)(&dtFractionInt) = 0;
					System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
					result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&dtFractionInt, ModType, XtraPar0, XtraPar1, XtraPar2, ptr, SymRate);
					System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
					dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
					dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
					TsRate = dtFractionInt2;
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
					throw;
				}
				map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
				try
				{
					global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, object ModPars, int SymRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmmbPars dtCmmbPars);
		global::_003CModule_003E.Dtapi_002EDtCmmbPars_002E_007Bctor_007D(&dtCmmbPars);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
			try
			{
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbsPars dtIsdbsPars);
				*(sbyte*)(&dtIsdbsPars) = 0;
				System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbsPars, sbyte>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbsPars, 1)) = 0;
				System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbtPars dtIsdbtPars);
				global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bctor_007D(&dtIsdbtPars);
				try
				{
					void* ptr = null;
					if (ModPars == null)
					{
						ptr = null;
					}
					else if (ModPars.GetType() == typeof(DtCmmbPars))
					{
						((DtCmmbPars)ModPars).ConvertToUnmgd(&dtCmmbPars);
						ptr = &dtCmmbPars;
					}
					else if (ModPars.GetType() == typeof(DtDvbC2Pars))
					{
						((DtDvbC2Pars)ModPars).ConvertToUnmgd(&dtDvbC2Pars);
						ptr = &dtDvbC2Pars;
					}
					else if (ModPars.GetType() == typeof(DtDvbT2Pars))
					{
						((DtDvbT2Pars)ModPars).ConvertToUnmgd(&dtDvbT2Pars);
						ptr = &dtDvbT2Pars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbsPars))
					{
						((DtIsdbsPars)ModPars).ConvertToUnmgd(&dtIsdbsPars);
						ptr = &dtIsdbsPars;
					}
					else if (ModPars.GetType() == typeof(DtIsdbtPars))
					{
						((DtIsdbtPars)ModPars).ConvertToUnmgd(&dtIsdbtPars);
						ptr = &dtIsdbtPars;
					}
					int num = 0;
					result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, ptr, SymRate);
					TsRate = num;
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbtPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbtPars_002E_007Bdtor_007D), &dtIsdbtPars);
					throw;
				}
				map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E* pThis = (map_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100));
				try
				{
					global::_003CModule_003E.std_002E_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_Tidy((_Tree_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
				}
				catch
				{
					//try-fault
					global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E*, void>)(&global::_003CModule_003E.std_002E_Tree_comp_alloc_003Cstd_003A_003A_Tmap_traits_003Cint_002Cint_002Cstd_003A_003Aless_003Cint_003E_002Cstd_003A_003Aallocator_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_0020_003E_002C0_003E_0020_003E_002E_007Bdtor_007D), pThis);
					throw;
				}
				global::_003CModule_003E.std_002E_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_002E_Freenode0_003Cclass_0020std_003A_003Aallocator_003Cstruct_0020std_003A_003A_Tree_node_003Cstruct_0020std_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E_0020_003E((allocator_003Cstd_003A_003A_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E_0020_003E*)System.Runtime.CompilerServices.Unsafe.AsPointer(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)), (_Tree_node_003Cstd_003A_003Apair_003Cint_0020const_0020_002Cint_003E_002Cvoid_0020_002A_003E*)(int)System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtIsdbtPars, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtIsdbtPars, 100)));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
				throw;
			}
			global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, DtIsdbTmmPars rTmmPars, int TsIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		uint result;
		try
		{
			rTmmPars.ConvertToUnmgd(&dtIsdbTmmPars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
			*(int*)(&dtFractionInt) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&dtFractionInt, &dtIsdbTmmPars, TsIdx);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
			dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
			dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
			TsRate = dtFractionInt2;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, DtIsdbTmmPars rTmmPars, int TsIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIsdbTmmPars dtIsdbTmmPars);
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bctor_007D(&dtIsdbTmmPars);
		uint result;
		try
		{
			rTmmPars.ConvertToUnmgd(&dtIsdbTmmPars);
			int num = 0;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, &dtIsdbTmmPars, TsIdx);
			TsRate = num;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIsdbTmmPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D), &dtIsdbTmmPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIsdbTmmPars_002E_007Bdtor_007D(&dtIsdbTmmPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, DtDvbT2Pars rT2Pars, int PlpIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			rT2Pars.ConvertToUnmgd(&dtDvbT2Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
			*(int*)(&dtFractionInt) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&dtFractionInt, &dtDvbT2Pars, PlpIdx);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
			dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
			dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
			TsRate = dtFractionInt2;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, DtDvbT2Pars rT2Pars, int PlpIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbT2Pars dtDvbT2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bctor_007D(&dtDvbT2Pars);
		uint result;
		try
		{
			rT2Pars.ConvertToUnmgd(&dtDvbT2Pars);
			int num = 0;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, &dtDvbT2Pars, PlpIdx);
			TsRate = num;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbT2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D), &dtDvbT2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbT2Pars_002E_007Bdtor_007D(&dtDvbT2Pars);
		return (DTAPI_RESULT)result;
	}

	public static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, DtDvbT2Pars rT2Pars)
	{
		return DtapiModPars2TsRate(ref TsRate, rT2Pars, 0);
	}

	public static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, DtDvbT2Pars rT2Pars)
	{
		return DtapiModPars2TsRate(ref TsRate, rT2Pars, 0);
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, DtDvbC2Pars rC2Pars, int PlpIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			rC2Pars.ConvertToUnmgd(&dtDvbC2Pars);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
			*(int*)(&dtFractionInt) = 0;
			System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&dtFractionInt, &dtDvbC2Pars, PlpIdx);
			System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
			dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
			dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
			TsRate = dtFractionInt2;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, DtDvbC2Pars rC2Pars, int PlpIdx)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtDvbC2Pars dtDvbC2Pars);
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bctor_007D(&dtDvbC2Pars);
		uint result;
		try
		{
			rC2Pars.ConvertToUnmgd(&dtDvbC2Pars);
			int num = 0;
			result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, &dtDvbC2Pars, PlpIdx);
			TsRate = num;
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtDvbC2Pars*, void>)(&global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D), &dtDvbC2Pars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtDvbC2Pars_002E_007Bdtor_007D(&dtDvbC2Pars);
		return (DTAPI_RESULT)result;
	}

	public static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, DtDvbC2Pars rC2Pars)
	{
		return DtapiModPars2TsRate(ref TsRate, rC2Pars, 0);
	}

	public static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, DtDvbC2Pars rC2Pars)
	{
		return DtapiModPars2TsRate(ref TsRate, rC2Pars, 0);
	}

	public static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2)
	{
		return DtapiModPars2TsRate(ref TsRate, ModType, XtraPar0, XtraPar1, XtraPar2, -1);
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, -1);
		TsRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref DtFractionInt TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, int SymRate)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtFractionInt dtFractionInt);
		*(int*)(&dtFractionInt) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4)) = 1;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&dtFractionInt, ModType, XtraPar0, XtraPar1, XtraPar2, SymRate);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out DtFractionInt dtFractionInt2);
		dtFractionInt2.m_Num = *(int*)(&dtFractionInt);
		dtFractionInt2.m_Den = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtFractionInt, int>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtFractionInt, 4));
		TsRate = dtFractionInt2;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiModPars2TsRate(ref int TsRate, int ModType, int XtraPar0, int XtraPar1, int XtraPar2, int SymRate)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiModPars2TsRate(&num, ModType, XtraPar0, XtraPar1, XtraPar2, SymRate);
		TsRate = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiVoltage2Power(int dBmV, ref int dBm)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiVoltage2Power(dBmV, &num, false);
		dBm = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiVoltage2Power(int dBmV, ref int dBm, [MarshalAs(UnmanagedType.U1)] bool Is50Ohm)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiVoltage2Power(dBmV, &num, Is50Ohm);
		dBm = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiPower2Voltage(int dBm, ref int rdBmV)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiPower2Voltage(dBm, &num, false);
		rdBmV = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiPower2Voltage(int dBm, ref int dBmV, [MarshalAs(UnmanagedType.U1)] bool Is50Ohm)
	{
		int num = 0;
		uint result = global::_003CModule_003E.Dtapi_002EDtapiPower2Voltage(dBm, &num, Is50Ohm);
		dBmV = num;
		return (DTAPI_RESULT)result;
	}

	public unsafe static string DtapiResult2Str(DTAPI_RESULT DtapiResult)
	{
		sbyte* ptr = global::_003CModule_003E.Dtapi_002EDtapiResult2Str((uint)DtapiResult);
		sbyte* ptr2 = ptr;
		if (*ptr != 0)
		{
			do
			{
				ptr2++;
			}
			while (*ptr2 != 0);
		}
		char[] array = new char[(nuint)(ptr2 - (nuint)ptr)];
		int num = 0;
		if (0 < (nint)array.LongLength)
		{
			do
			{
				ref char reference = ref array[num];
				reference = (char)(*(num + ptr));
				num++;
			}
			while (num < (nint)array.LongLength);
		}
		return new string(array);
	}

	public unsafe static DTAPI_RESULT DtapiInitDtIpParsFromIpString(ref DtIpPars rIpPars, string rDstIp, string rSrcIp)
	{
		char* ptr = null;
		if (rDstIp != null)
		{
			uint num = (uint)(rDstIp.Length + 1);
			ptr = (char*)global::_003CModule_003E.new_005B_005D((num > int.MaxValue) ? uint.MaxValue : (num << 1));
			int num2 = 0;
			if (0 < rDstIp.Length)
			{
				do
				{
					*(char*)(num2 * 2 + (byte*)ptr) = rDstIp[num2];
					num2++;
				}
				while (num2 < rDstIp.Length);
			}
			*(short*)(rDstIp.Length * 2 + (byte*)ptr) = 0;
		}
		char* ptr2 = null;
		if (rSrcIp != null)
		{
			uint num3 = (uint)(rSrcIp.Length + 1);
			ptr2 = (char*)global::_003CModule_003E.new_005B_005D((num3 > int.MaxValue) ? uint.MaxValue : (num3 << 1));
			int num4 = 0;
			if (0 < rSrcIp.Length)
			{
				do
				{
					*(char*)(num4 * 2 + (byte*)ptr2) = rSrcIp[num4];
					num4++;
				}
				while (num4 < rSrcIp.Length);
			}
			*(short*)(rSrcIp.Length * 2 + (byte*)ptr2) = 0;
		}
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtIpPars dtIpPars);
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bctor_007D(&dtIpPars);
		uint result;
		try
		{
			result = global::_003CModule_003E.Dtapi_002EDtapiInitDtIpParsFromIpString(&dtIpPars, ptr, ptr2);
			if (ptr != null)
			{
				global::_003CModule_003E.delete_005B_005D(ptr);
			}
			if (ptr2 != null)
			{
				global::_003CModule_003E.delete_005B_005D(ptr2);
			}
			(rIpPars = new DtIpPars()).ConvertFromUnmgd(&dtIpPars);
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.___CxxCallUnwindDtor((delegate*<void*, void>)(delegate*<Dtapi.DtIpPars*, void>)(&global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D), &dtIpPars);
			throw;
		}
		global::_003CModule_003E.Dtapi_002EDtIpPars_002E_007Bdtor_007D(&dtIpPars);
		return (DTAPI_RESULT)result;
	}

	public unsafe static DTAPI_RESULT DtapiIpAddr2ByteArray(string rIpStr, byte[] rIpByte, ref int Flags)
	{
		uint num = (uint)(rIpStr.Length + 1);
		char* ptr = (char*)global::_003CModule_003E.new_005B_005D((num > int.MaxValue) ? uint.MaxValue : (num << 1));
		int num2 = 0;
		if (0 < rIpStr.Length)
		{
			do
			{
				*(char*)(num2 * 2 + (byte*)ptr) = rIpStr[num2];
				num2++;
			}
			while (num2 < rIpStr.Length);
		}
		*(short*)(rIpStr.Length * 2 + (byte*)ptr) = 0;
		fixed (byte* ptr2 = &rIpByte[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out int num3);
			uint result = global::_003CModule_003E.Dtapi_002EDtapiIpAddr2ByteArray(ptr, ptr2, &num3);
			global::_003CModule_003E.delete_005B_005D(ptr);
			Flags = num3;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe static DTAPI_RESULT DtapiIpAddr2Str(ref string rStr, byte[] rIpAddr)
	{
		fixed (byte* ptr = &rIpAddr[0])
		{
			System.Runtime.CompilerServices.Unsafe.SkipInit(out _0024ArrayType_0024_0024_0024BY0GE_0040_W _0024ArrayType_0024_0024_0024BY0GE_0040_W);
			uint result = global::_003CModule_003E.Dtapi_002EDtapiIpAddr2Str((char*)(&_0024ArrayType_0024_0024_0024BY0GE_0040_W), 100, ptr);
			rStr = new string((char*)(&_0024ArrayType_0024_0024_0024BY0GE_0040_W));
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe static DTAPI_RESULT DtapiStr2IpAddr(byte[] rIpAddr, string rStr)
	{
		uint num = (uint)(rStr.Length + 1);
		char* ptr = (char*)global::_003CModule_003E.new_005B_005D((num > int.MaxValue) ? uint.MaxValue : (num << 1));
		int num2 = 0;
		if (0 < rStr.Length)
		{
			do
			{
				*(char*)(num2 * 2 + (byte*)ptr) = rStr[num2];
				num2++;
			}
			while (num2 < rStr.Length);
		}
		*(short*)(rStr.Length * 2 + (byte*)ptr) = 0;
		fixed (byte* ptr2 = &rIpAddr[0])
		{
			uint result = global::_003CModule_003E.Dtapi_002EDtapiStr2IpAddr(ptr2, ptr);
			global::_003CModule_003E.delete_005B_005D(ptr);
			return (DTAPI_RESULT)result;
		}
	}
}
