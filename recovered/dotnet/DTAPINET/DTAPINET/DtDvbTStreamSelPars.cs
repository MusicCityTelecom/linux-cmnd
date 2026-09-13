using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

[StructLayout(LayoutKind.Sequential, Size = 1)]
public struct DtDvbTStreamSelPars
{
	internal unsafe void ConvertToUnmgd(Dtapi.DtDvbTStreamSelPars* umSelPars)
	{
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDvbTStreamSelPars* umSelPars)
	{
	}
}
