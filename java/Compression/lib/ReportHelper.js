/**
 * http://usejsdoc.org/
 */
var nconf = require('nconf'), fs = require('fs'),
	archiver = require('archiver'),nodemailer = require('nodemailer');;

(function main() {
	nconf.file({ file: '..\\conf\\config.json' });
	process(function(err, result) {
		if (err) {
			console.log("Error in processing: " + err.code + ", " + err.message);
			process.exit(-1);
		} else {
			console.log("Successfully processed." + result);
		}
	});
})();

function process(callback) {
	var writeStream = fs.createWriteStream(nconf.get("zipFileName"));
	var input = archiver("zip");
	input.on("error", function(err){
	    callback(err);
	});
	
	input.pipe(writeStream);
	input.bulk([{ expand: true, cwd: "", src: nconf.get("csvpath"), dest: ""}]);
	input.finalize();
	
	var transporter = nodemailer.createTransport(nconf.get("email.smtpserver"));
	var mailOptions = {
			from: nconf.get("email.from"),
			to: nconf.get("email.To"),
			subject: nconf.get("email.subject"),
			text: nconf.get("email.body"),
			attachments: [
			        {
			        	filename: "report",
			        	path: nconf.get("zipFileName")
			        }
			]
	};
	transporter.sendMail(mailOptions, function(err, info) {
		if(err){
			callback(err);
		} else {
			callback(null, info.response);
		}
	});
}