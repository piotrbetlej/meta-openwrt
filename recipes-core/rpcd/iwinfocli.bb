INSANE_SKIP_${PN} += "dev-so installed-vs-shipped file-rdeps dep-cmp build-deps"

DESCRIPTION = "This package provides the UBUS RPC backend server to expose various functionality to frontend programs via JSON-RPC."
HOMEPAGE = "http://wiki.openwrt.org/doc/techref/rpcd"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=18;md5=da5faf55ed0618f0dde1c88e76a0fc74"

SRC_URI = "git://github.com/piotrbetlej/rpcd;protocol=https;branch=iwinfo_cli_yocto_poky \
           file://rpcd.init \
           file://rpcd.config \
           "
		   
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"

inherit cmake

PR="r1"

DEPENDS = "uci iwinfo libnl"
RDEPENDS_${PN} += "uci iwinfo libnl-genl"

EXTRA_OECMAKE = "-DFILE_SUPPORT=OFF -DIWINFO_SUPPORT=OFF"

#FIXME: put plugins to the correct place
FILES_${PN} += "/usr/lib/*.so /usr/share/rpcd*"

#FIXME: install rpcd directly to /sbin from makefile instead of usig mv
do_install_append () {
	mkdir -p ${D}/${sysconfdir}/init.d ${D}/${sysconfdir}/config ${D}/usr/share/rpcd/acl.d
	install ${WORKDIR}/rpcd.config ${D}/${sysconfdir}/config/rpcd
	install ${WORKDIR}/rpcd.init ${D}/${sysconfdir}/init.d/rpcd 
	install ${WORKDIR}/rpcd.init ${D}/${sysconfdir}/init.d/rpcd
	install ${S}/unauthenticated.json ${D}/usr/share/rpcd/acl.d
	cp -a ${S}/include ${D}/usr/include
	mv ${D}/usr/sbin ${D}/sbin
}