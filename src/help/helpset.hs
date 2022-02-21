<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN"
"http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="2.0">
	<title>Ayuda App Hotel</title>
	<maps>
		<homeID>init</homeID>
		<mapref location="map.jhm"/>
	</maps>
	<view xml:lang="es" mergetype="javax.help.UniteAppendMerge">
		<name>TOC</name>
		<label>Tabla de contenidos</label>
		<type>javax.help.TOCView</type>
		<data>TOC.xml</data>
	</view>
	<view xml:lang="es" mergetype="javax.help.SortMerge">
		<name>Index</name>
		<label>Indice</label>
		<type>javax.help.IndexView</type>
		<data>index.xml</data>
	</view>
	<view xml:lang="es">
		<name>Buscar</name>
		<label>Buscar</label>
		<type>javax.help.SearchView</type>
		<data engine="com.sun.java.help.search.DefaultSearchEngine">JavaHelpSearch</data>
	</view>
	<view>
		<name>favoritos</name>
		<label>Favoritos</label>
		<type>javax.help.FavoritesView</type>
	</view>
	<presentation default=true>
		<name>Ventana principal</name>
		<size width="400" height="400" />
		<location x="200" y="200" />
		<title>Visor de Ayuda</title>
		<toolbar>
			<helpaction>javax.help.BackAction</helpaction>
			<helpaction>javax.help.ForwardAction</helpaction>
			<helpaction image="logo">javax.help.HomeAction</helpaction>
		</toolbar>
	</presentation>
</helpset>
