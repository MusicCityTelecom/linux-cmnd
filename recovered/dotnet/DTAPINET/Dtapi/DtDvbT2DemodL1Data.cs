using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 192)]
[NativeCppClass]
[UnsafeValueType]
internal struct DtDvbT2DemodL1Data
{
	[StructLayout(LayoutKind.Sequential, Size = 20)]
	[CLSCompliant(false)]
	[NativeCppClass]
	[UnsafeValueType]
	public struct DtDvbT2DemodL1P1
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 108)]
	[UnsafeValueType]
	[NativeCppClass]
	[CLSCompliant(false)]
	public struct DtDvbT2DemodL1Pre
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 64)]
	[NativeCppClass]
	[CLSCompliant(false)]
	public struct DtDvbT2DemodL1Post
	{
	}
}
