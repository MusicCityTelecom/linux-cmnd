using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace std;

[StructLayout(LayoutKind.Sequential, Size = 56)]
[NativeCppClass]
internal struct ios_base
{
	[NativeCppClass]
	[CLSCompliant(false)]
	public enum @event
	{

	}

	[StructLayout(LayoutKind.Sequential, Size = 20)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct failure
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 1)]
	[NativeCppClass]
	[CLSCompliant(false)]
	public struct Init
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 16)]
	[NativeCppClass]
	internal struct _Iosarray
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 12)]
	[NativeCppClass]
	internal struct _Fnarray
	{
	}
}
