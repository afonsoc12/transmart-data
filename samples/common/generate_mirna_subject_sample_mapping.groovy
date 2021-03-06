/*
 * Copyright © 2013-2014 The Hyve B.V.
 *
 * This file is part of transmart-data.
 *
 * Transmart-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * transmart-data.  If not, see <http://www.gnu.org/licenses/>.
 */

@Grab(group = 'net.sf.opencsv', module = 'opencsv', version = '2.3')
import au.com.bytecode.opencsv.CSVWriter
import au.com.bytecode.opencsv.CSVReader

def parseOptions() {
	def cli = new CliBuilder(usage: "generate_mirna_subject_sample_mapping.groovy")
	cli.p 'Platform id', required: true, longOpt: 'gpl_id', args: 1, argName: 'gpl_id'
	cli.t 'Trial id', required: true, longOpt: 'trial_id', args: 1, argName: 'trial_id'
	cli.y 'Tissue type', longOpt: 'tissue', args: 1, argName: 'tissue'
	cli.i 'input files directory', longOpt: 'input_dir', args: 1, argName: 'dir'
	cli.o 'tsv output file; stdout if unspecified', longOpt: 'output', args: 1, argName: 'file'
	def options = cli.parse(args)
	options
}

options = parseOptions()
if (!options) {
    System.exit 1
}

CSVWriter writer = new CSVWriter(new OutputStreamWriter(options.o ? new FileOutputStream(options.o) : System.out, 'UTF-8'), '\t' as char)
try {
	writer.writeNext(['trial_name', 'site_id', 'subject_id', 'sample_cd', 'platform', 'tissue_type', 'attr1', 'attr2', 'cat_cd', 'src_cd'] as String[])
	String gplId = options.p
	String trialId = options.t
	String tissue = options.y ?: 'Unknown'
	def inputDir = new File(options.i)
	def matches = [:]
	inputDir.listFiles().each { file ->
		def matcher = file.name =~ /^(.*)_GeneView.txt$/
		if (matcher) {
			matches[(matcher)] = file
		}
	}
	matches.each { entry ->
		//TODO site_id is really used in calculations. Needs to be not null value
		def matcher = entry.key
		writer.writeNext(
			[trialId, 'NA', matcher[0][1], matcher[0][1], gplId, tissue, null, null, 'Biomarker_Data+PLATFORM+ATTR1', 'STD' ] as String[])
	}
} finally {
	writer.close()
}
