import { goto } from '$app/navigation';

import type { components, paths } from '$lib/types/api/v1/schema';
import type { Page } from '@sveltejs/kit';
import createClient, { type DefaultParamsOption } from 'openapi-fetch';

import toastr from 'toastr';
import 'toastr/build/toastr.css';

toastr.options = {
  showDuration: 300,
  hideDuration: 300,
  timeOut: 3000,
  extendedTimeOut: 1000
};

class Rq {
  public member: components['schemas']['MemberMeDto'];
  public inventories: ReturnType<typeof this.makeReactivityInventories>;
  public profileInventories: ReturnType<typeof this.makeReactivityProfileInventories>;

  public verifiedPassword: boolean = false;

  public SITE_NAME: String = "코드이썬";

  constructor() {
    this.member = this.makeReactivityMember();
    this.inventories = this.makeReactivityInventories();
    this.profileInventories = this.makeReactivityProfileInventories();
  }

  public effect(fn: () => void) {
    $effect(fn);
  }

  public isAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_CLASSADMIN');
  }

  public isSystemAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_SYSTEMADMIN');
  }

  public isSuperAdmin() {
    if (this.isLogout()) return false;

    return this.member.authorities.includes('ROLE_SUPERADMIN');
  }

  public isAdmPage($page: Page<Record<string, string>>) {
    return $page.url.pathname.startsWith('/adm/');
  }

  public isUsrPage($page: Page<Record<string, string>>) {
    return !this.isAdmPage($page);
  }

  // URL
  public goTo(url: string) {
    goto(url);
  }

  public replace(url: string) {
    goto(url, { replaceState: true });
  }

  public reload() {
    this.replace('/redirect?url=' + window.location.href);
  }

  public goToCurrentPageWithNewParam(name: string, value: string) {
    const currentUrl = new URL(window.location.href);

    const searchParams = currentUrl.searchParams;

    searchParams.set(name, value);

    this.goToCurrentPageWithNewQueryStr(searchParams.toString());
  }

  public goToCurrentPageWithNewQueryStr(query: string) {
    this.goTo(window.location.pathname + '?' + query);
  }

  // API END POINTS
  public apiEndPoints() {
    return createClient<paths>({
      baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
      credentials: 'include'
    });
  }

  public apiEndPointsWithFetch(fetch: any) {
    return createClient<paths>({
      baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
      credentials: 'include',
      fetch
    });
  }

  // MSG, REDIRECT
  public msgAndRedirect(
    data: { msg: string } | undefined,
    error: { msg: string } | undefined,
    url: string,
    callback?: () => void
  ) {
    if (data) {
      this.msgInfo(data.msg);
      this.replace(url);
    }
    if (error) {
      this.msgError(error.msg);
    }

    if (callback) window.setTimeout(callback, 100);
  }

  public msgInfo(message: string) {
    toastr.info(message);
  }

  public msgError(message: string) {
    toastr.error(message);
  }

  public equipItem(inventoryId: number) {
    const targetInventory = rq.inventories.all.find(inv => inv.id === inventoryId);

    if (targetInventory) {
      rq.inventories.all.forEach(inventory => {
        if (inventory.item.itemPartsId === targetInventory.item.itemPartsId) {
          inventory.isEquipped = false;
        }
      });
  
      targetInventory.isEquipped = true;
    }
  }

  public unEquipItem(inventoryId: number | undefined) {
    const inventory = this.inventories.all.find(inv => inv.id === inventoryId);
    if (inventory) {
      inventory.isEquipped = false;
    }
  }

  public equipProfile(profileId: number) {
    rq.profileInventories.all.forEach(profile => {
      if(profile.profileIcon.id === profileId) {
        profile.isEquipped = true;
      } else {
        profile.isEquipped = false;
      }
    });
  }

  public makeReactivityInventories() {
    const inventories = $state([] as Array<components['schemas']['InventoryDto']>);
    
    return {
      get all() {
        return inventories;
      },

      get unequipped() {
        return inventories.filter(inventory => !inventory.isEquipped);
      },

      add(inventory: components['schemas']['InventoryDto']) {
        inventories.push(inventory);
      },

      remove(inventoryId: number) {
        const index = inventories.findIndex(inventory => inventory.id === inventoryId);
        if (index !== -1) {
          inventories.splice(index, 1);
        }
      },

      findEquippedByItemPartsId(itemPartsId: number) {
        return inventories.find(inventory => 
          inventory.item.itemPartsId === itemPartsId && inventory.isEquipped
        );
      },

      isItemOwned(itemId: number) {
        return inventories.some(inventory => inventory.item.id === itemId);
      }

    };
  }

  public makeReactivityProfileInventories() {
    const profileInventories = $state([] as Array<components['schemas']['ProfileInventoryDto']>);

    return {
      get all() {
        return profileInventories;
      },

      add(profile: components['schemas']['ProfileInventoryDto']) {
        profileInventories.push(profile);
      },

      findEquippedProfil() {
        return profileInventories.find(profile => profile.isEquipped);
      },

      isProfileOwned(profileId: number) {
        return profileInventories.some(inventory => inventory.profileIcon.id === profileId);
      }
    };

  }

  public makeReactivityMember() {
    let id = $state(0);
    let name = $state('');
    let username = $state('');
    let createDate = $state('');
    let modifyDate = $state('');
    let cellphoneNo = $state('');
    let authorities: string[] = $state([]);

    let player = $state({
      id: 0,
      createDate: '',
      modifyDate: '',
      nickname: '',
      exp: 0,
      gems: 0,
      characterType: 0,
      backgroundVolume: 0,
      effectVolume: 0,
      editorAutoComplete: 1,
      editorAutoClose: 1
    });

    return {
      get id() {
        return id;
      },
      set id(value: number) {
        id = value;
      },
      get createDate() {
        return createDate;
      },
      set createDate(value: string) {
        createDate = value;
      },
      get modifyDate() {
        return modifyDate;
      },
      set modifyDate(value: string) {
        modifyDate = value;
      },
      get username() {
        return username;
      },
      set username(value: string) { 
        username = value;
      },
      get name() {
        return name;
      },
      set name(value: string) {
        name = value;
      },
      get cellphoneNo() {
        return cellphoneNo;
      },
      set cellphoneNo(value: string) {
        cellphoneNo = value;
      },
      get authorities() {
        return authorities;
      },
      set authorities(value: string[]) {
        authorities = value;
      },
      get player() {
        return player;
      },
      set player(value: components['schemas']['PlayerDto']) {
        player.id = value.id;
        player.createDate = value.createDate;
        player.modifyDate = value.modifyDate;
        player.nickname = value.nickname;
        player.exp = value.exp;
        player.gems = value.gems;
        player.characterType = value.characterType;
        player.backgroundVolume = value.backgroundVolume;
        player.effectVolume = value.effectVolume;
        player.editorAutoComplete = value.editorAutoComplete;
        player.editorAutoClose = value.editorAutoClose;
      }
    };
  }

  public getAuthToString() {
    return this.member.authorities.map(authority => {
      switch (authority) {
        case 'ROLE_CLASSADMIN':
          return '학급관리자'; 
        case 'ROLE_SYSTEMADMIN':
          return '사업관리자'; 
        case 'ROLE_SUPERADMIN':
          return '최고관리자'; 
        default: 
          return '';
      }
    }).filter(Boolean);
  }

  public getPlayerLeve() { // ToDo: 레벨 계산식 필요
    let level = 1;
    let requiredExp = 6;
    let exp = this.member.player.exp;

    while (exp >= requiredExp) {
        exp -= requiredExp;
        level++;
        requiredExp += 6;
    }

    return level;
  }
  

  public setLogined(member: components['schemas']['MemberMeDto']) {
    Object.assign(this.member, member);
  }

  public setLogout() {
    this.member.id = 0;
    this.member.createDate = '';
    this.member.modifyDate = '';
    this.member.name = '';
    this.member.username = '';
    this.member.cellphoneNo = '';
    this.member.authorities = [];	
    this.member.player = { 
      id: 0, createDate: '', modifyDate: '', nickname: '', exp: 0, gems: 0, characterType: 0, backgroundVolume: 0, effectVolume: 0, editorAutoComplete: 1, editorAutoClose: 1
    };
    while (this.inventories.all.length > 0) {
      this.inventories.all.pop();
    }
    while(this.profileInventories.all.length > 0) {
      this.profileInventories.all.pop();
    }
  }

  public isLogin() {
    return this.member.id !== 0;
  }

  public isLogout() {
    return !this.isLogin();
  }

  public async initAuth() {
    const { data } = await this.apiEndPoints().GET('/api/v1/members/me');

    if (data) {
      this.setLogined(data.data.item);
    }
  }

  public async logoutAndRedirect(url: string) {
    await this.apiEndPoints().POST('/api/v1/members/logout');

    this.setLogout();
    this.replace(url);
  }

  public async fetchAndInitializeInventories() {
    const { data } = await this.apiEndPoints().GET('/api/v1/inventory/myInventory'); 
    if (data) {
      while (this.inventories.all.length > 0) {
        this.inventories.all.pop();
      }
      data.data.inventoryDto.forEach(inventory => this.inventories.add(inventory));
    }
  }

  public async fetchAndInitializeProfileInventories() {
    const { data } = await this.apiEndPoints().GET('/api/v1/profileInventory/myInventory'); 
    if (data) {
      while (this.profileInventories.all.length > 0) {
        this.profileInventories.all.pop();
      }
      data.data.profileInventoryDtoList.forEach(profile => this.profileInventories.add(profile));
    }
  }

  public async test() {
    const { data } = await this.apiEndPoints().GET('/api/v1/members/test');

    if (data) {
      return data.data.idList;
    }
  }


}

const rq = new Rq();

export default rq;