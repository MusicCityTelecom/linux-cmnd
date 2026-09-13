using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 48)]
[UnsafeValueType]
[NativeCppClass]
internal struct DtStatistic
{
	[NativeCppClass]
	[CLSCompliant(false)]
	public enum StatValueType
	{

	}

	[CLSCompliant(false)]
	[NativeCppClass]
	public enum IdXtraType
	{

	}

	[CLSCompliant(false)]
	[NativeCppClass]
	public enum TimeWindowType
	{

	}

	[StructLayout(LayoutKind.Explicit, Size = 8)]
	[NativeCppClass]
	internal struct _0024UnnamedClass_00240x711140a3_002455_0024
	{
	}
}
