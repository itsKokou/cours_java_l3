package ism.repositories.bd;

import ism.entities.Module;

public interface ModuleRepository extends Repository<Module>{
    int update (Module data);
}
