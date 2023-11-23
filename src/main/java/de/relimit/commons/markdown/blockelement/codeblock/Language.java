package de.relimit.commons.markdown.blockelement.codeblock;

/**
 * An enum providing {@link CodeBlockLanguage}s for tagging a code block so the
 * code is properly formatted. The languages are not standardized. This enum
 * contains all we could find at the time of this writing. The interface exists
 * so everyone can add additional languages.
 */
public enum Language implements CodeBlockLanguage {

	UNKNOWN(""),
	CUCUMBER("Cucumber"),
	ABAP("abap"),
	ADA("ada"),
	AHK("ahk"),
	APACHECONF("apacheconf"),
	APPLESCRIPT("applescript"),
	AS("as"),
	AS3("as3"),
	ASY("asy"),
	BASH("bash"),
	BAT("bat"),
	BEFUNGE("befunge"),
	BLITZMAX("blitzmax"),
	BOO("boo"),
	BRAINFUCK("brainfuck"),
	C("c"),
	CFM("cfm"),
	CHEETAH("cheetah"),
	CL("cl"),
	CLOJURE("clojure"),
	CMAKE("cmake"),
	COFFEESCRIPT("coffeescript"),
	CONSOLE("console"),
	CONTROL("control"),
	CPP("cpp"),
	CSHARP("csharp"),
	CSS("css"),
	CYTHON("cython"),
	D("d"),
	DELPHI("delphi"),
	DIFF("diff"),
	DPATCH("dpatch"),
	DUEL("duel"),
	DYLAN("dylan"),
	ERB("erb"),
	ERL("erl"),
	ERLANG("erlang"),
	EVOQUE("evoque"),
	FACTOR("factor"),
	FELIX("felix"),
	FORTRAN("fortran"),
	GAS("gas"),
	GENSHI("genshi"),
	GLSL("glsl"),
	GNUPLOT("gnuplot"),
	GO("go"),
	GROFF("groff"),
	HAML("haml"),
	HASKELL("haskell"),
	HTML("html"),
	HX("hx"),
	HYBRIS("hybris"),
	INI("ini"),
	IO("io"),
	IOKE("ioke"),
	IRC("irc"),
	JADE("jade"),
	JAVA("java"),
	JS("js"),
	JSP("jsp"),
	LHS("lhs"),
	LLVM("llvm"),
	LOGTALK("logtalk"),
	LUA("lua"),
	MAKE("make"),
	MAKO("mako"),
	MAQL("maql"),
	MASON("mason"),
	MARKDOWN("markdown"),
	MODELICA("modelica"),
	MODULA2("modula2"),
	MOOCODE("moocode"),
	MUPAD("mupad"),
	MXML("mxml"),
	MYGHTY("myghty"),
	NASM("nasm"),
	NEWSPEAK("newspeak"),
	OBJDUMP("objdump"),
	OBJECTIVEC("objectivec"),
	OBJECTIVEJ("objectivej"),
	OCAML("ocaml"),
	OOC("ooc"),
	PERL("perl"),
	PHP("php"),
	POSTSCRIPT("postscript"),
	POT("pot"),
	POV("pov"),
	PROLOG("prolog"),
	PROPERTIES("properties"),
	PROTOBUF("protobuf"),
	PY3TB("py3tb"),
	PYTB("pytb"),
	PYTHON("python"),
	R("r"),
	RB("rb"),
	RCONSOLE("rconsole"),
	REBOL("rebol"),
	REDCODE("redcode"),
	RHTML("rhtml"),
	RST("rst"),
	SASS("sass"),
	SCALA("scala"),
	SCAML("scaml"),
	SCHEME("scheme"),
	SCSS("scss"),
	SMALLTALK("smalltalk"),
	SMARTY("smarty"),
	SOURCESLIST("sourceslist"),
	SPLUS("splus"),
	SQL("sql"),
	SQLITE3("sqlite3"),
	SQUIDCONF("squidconf"),
	SSP("ssp"),
	TCL("tcl"),
	TCSH("tcsh"),
	TEX("tex"),
	TEXT("text"),
	V("v"),
	VALA("vala"),
	VBNET("vbnet"),
	VELOCITY("velocity"),
	VIM("vim"),
	XML("xml"),
	XQUERY("xquery"),
	XSLT("xslt"),
	YAML("yaml");

	private String name;

	private Language(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}