using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 68)]
[NativeCppClass]
internal struct DtEncVidParsMp2V
{
	[CLSCompliant(false)]
	[NativeCppClass]
	public enum Mp2VProfile
	{

	}

	[CLSCompliant(false)]
	[NativeCppClass]
	public enum Mp2VLevel
	{

	}

	[NativeCppClass]
	[CLSCompliant(false)]
	public enum IntraVlcFormat
	{

	}

	[NativeCppClass]
	[CLSCompliant(false)]
	public enum QScaleType
	{

	}
}
