package io.github.pleuvoir.migration.shell;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("迁移指令")
public class MigrateCommand {


	@Autowired
	private Flyway flyway;

	@ShellMethod("Starts the database migration. All pending migrations will be applied in order.Calling migrate on an up-to-date database has no effect.")
	public void migrate() {
		flyway.migrate();
	}

	@ShellMethod("Retrieves the complete information about all the migrations including applied, pending and current migrations with details and status.")
	public void info() {
		flyway.info();
	}

	@ShellMethod("Validate applied migrations against resolved ones (on the filesystem or classpath) to detect accidental changes that may prevent the schema(s) from being recreated exactly.")
	public void validate() {
		flyway.validate();
	}

	@ShellMethod("Baselines an existing database, excluding all migrations up to and including baselineVersion.")
	public void baseline() {
		flyway.baseline();
	}

	@ShellMethod("Repairs the Flyway metadata table.")
	public void repair() {
		flyway.repair();
	}

}
