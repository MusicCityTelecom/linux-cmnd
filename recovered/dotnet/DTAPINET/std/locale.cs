using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace std;

[StructLayout(LayoutKind.Sequential, Size = 8)]
[NativeCppClass]
internal struct locale
{
	[StructLayout(LayoutKind.Sequential, Size = 4)]
	[NativeCppClass]
	[CLSCompliant(false)]
	public struct id
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 32)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct _Locimp
	{
	}

	[StructLayout(LayoutKind.Sequential, Size = 8)]
	[CLSCompliant(false)]
	[NativeCppClass]
	public struct facet
	{
	}
}
