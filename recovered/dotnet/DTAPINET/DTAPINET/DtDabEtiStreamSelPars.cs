using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

[StructLayout(LayoutKind.Sequential, Size = 1)]
public struct DtDabEtiStreamSelPars
{
	internal unsafe void ConvertToUnmgd(Dtapi.DtDabEtiStreamSelPars* uStremSel)
	{
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtDabEtiStreamSelPars* uStremSel)
	{
	}
}
