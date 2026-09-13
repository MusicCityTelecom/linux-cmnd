using System;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Dtapi;

[StructLayout(LayoutKind.Sequential, Size = 64)]
[NativeCppClass]
internal struct DtEncVidParsH264
{
	[NativeCppClass]
	[CLSCompliant(false)]
	public enum H264Profile
	{

	}

	[NativeCppClass]
	[CLSCompliant(false)]
	public enum H264Level
	{

	}

	[CLSCompliant(false)]
	[NativeCppClass]
	public enum CodingMode
	{

	}
}
