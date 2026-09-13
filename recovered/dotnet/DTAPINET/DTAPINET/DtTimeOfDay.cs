using System.Runtime.CompilerServices;
using Dtapi;

namespace DTAPINET;

public class DtTimeOfDay
{
	public static uint NANOSEC_IN_SEC = 1000000000u;

	public static long MAX_IN_NANOSEC = 4294967296000000000L;

	public uint m_Seconds;

	public uint m_Nanoseconds;

	public unsafe DtTimeOfDay(uint Sec, uint Nanosec)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, Sec, Nanosec);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
	}

	public unsafe DtTimeOfDay(long TimeInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, TimeInNs);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
	}

	public unsafe DtTimeOfDay()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
	}

	[SpecialName]
	public unsafe DtTimeOfDay op_Assign(long TimeInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, TimeInNs);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return this;
	}

	[SpecialName]
	public DtTimeOfDay op_Assign(DtTimeOfDay Oth)
	{
		m_Seconds = Oth.m_Seconds;
		m_Nanoseconds = Oth.m_Nanoseconds;
		return this;
	}

	public unsafe implicit operator long()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		return (long)global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002E_K(&dtTimeOfDay);
	}

	public unsafe DtTimeOfDay operator +(long DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002B_003D(&dtTimeOfDay, DeltaInNs);
		DtTimeOfDay dtTimeOfDay2 = new DtTimeOfDay();
		dtTimeOfDay2.m_Seconds = *(uint*)(&dtTimeOfDay);
		dtTimeOfDay2.m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return dtTimeOfDay2;
	}

	public unsafe DtTimeOfDay operator +(int DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002B_003D(&dtTimeOfDay, DeltaInNs);
		DtTimeOfDay dtTimeOfDay2 = new DtTimeOfDay();
		dtTimeOfDay2.m_Seconds = *(uint*)(&dtTimeOfDay);
		dtTimeOfDay2.m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return dtTimeOfDay2;
	}

	[SpecialName]
	public unsafe DtTimeOfDay op_AdditionAssignment(long DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002B_003D(&dtTimeOfDay, DeltaInNs);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return this;
	}

	public unsafe long operator -(DtTimeOfDay Oth)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay2);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay2, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		*(uint*)(&dtTimeOfDay2) = Oth.m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay2, 4)) = Oth.m_Nanoseconds;
		return global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002D(&dtTimeOfDay, &dtTimeOfDay2);
	}

	public unsafe DtTimeOfDay operator -(long DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002D_003D(&dtTimeOfDay, DeltaInNs);
		DtTimeOfDay dtTimeOfDay2 = new DtTimeOfDay();
		dtTimeOfDay2.m_Seconds = *(uint*)(&dtTimeOfDay);
		dtTimeOfDay2.m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return dtTimeOfDay2;
	}

	public unsafe DtTimeOfDay operator -(int DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002D_003D(&dtTimeOfDay, DeltaInNs);
		DtTimeOfDay dtTimeOfDay2 = new DtTimeOfDay();
		dtTimeOfDay2.m_Seconds = *(uint*)(&dtTimeOfDay);
		dtTimeOfDay2.m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return dtTimeOfDay2;
	}

	[SpecialName]
	public unsafe DtTimeOfDay op_SubtractionAssignment(long DeltaInNs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtTimeOfDay dtTimeOfDay);
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_007Bctor_007D(&dtTimeOfDay, 0u, 0u);
		*(uint*)(&dtTimeOfDay) = m_Seconds;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4)) = m_Nanoseconds;
		global::_003CModule_003E.Dtapi_002EDtTimeOfDay_002E_002B_003D(&dtTimeOfDay, DeltaInNs);
		m_Seconds = *(uint*)(&dtTimeOfDay);
		m_Nanoseconds = System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtTimeOfDay, uint>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtTimeOfDay, 4));
		return this;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtTimeOfDay* Unmgd)
	{
		m_Seconds = *(uint*)Unmgd;
		m_Nanoseconds = ((uint*)Unmgd)[1];
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtTimeOfDay* Unmgd)
	{
		*(uint*)Unmgd = m_Seconds;
		((int*)Unmgd)[1] = (int)m_Nanoseconds;
	}
}
