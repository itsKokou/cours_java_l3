package ism.services;

import ism.entities.Module;

public interface ModuleService extends Service <Module> {
    int update (Module data);
}
